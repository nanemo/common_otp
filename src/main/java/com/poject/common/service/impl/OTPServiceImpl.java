package com.poject.common.service.impl;

import com.poject.common.config.ApplicationConfiguration;
import com.poject.common.constants.OTPStatus;
import com.poject.common.model.dto.CommonResponse;
import com.poject.common.model.dto.OTPResponse;
import com.poject.common.model.dto.SMSRequestDto;
import com.poject.common.model.entity.OTPEntity;
import com.poject.common.repository.OTPEntityRepository;
import com.poject.common.service.OTPService;
import com.poject.common.service.SmsService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import static java.lang.Boolean.valueOf;

@Service
public class OTPServiceImpl implements OTPService {
    private static final Logger LOGGER = LoggerFactory.getLogger("businessLogger");
    private static final Random randomObject = new Random();
    private final SmsService smsService;
    private final OTPEntityRepository otpEntityRepository;
    private final ApplicationConfiguration applicationConfiguration;

    public OTPServiceImpl(SmsService smsService, OTPEntityRepository otpEntityRepository, ApplicationConfiguration applicationConfiguration) {
        this.smsService = smsService;
        this.otpEntityRepository = otpEntityRepository;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public ResponseEntity<OTPResponse> generateOtp(Integer length, String send, OTPEntity otpEntity) {

        LOGGER.info("REQUEST: {}", otpEntity);
        //CHECKING INPUT --OTP MUST BETWEEN 4 TO 9--
        if (length < 4 || length > 9) return ResponseEntity.badRequest().build();
        int otp = 0;
        Boolean sendSMS = valueOf(send);
        int fullDigit = (int) Math.pow(10, length);
        for (int i = 1; i < fullDigit; i *= 10) {
            int random = randomObject.nextInt(9) + 1;
            otp = otp + i * random;
        }
        String smsText = otpEntity.getSmsText();
        if (smsText != null) {
            smsText = smsText.replace("{otp}", String.valueOf(otp));
        }
        LOGGER.info("OTP : {}", otp);
        // set otp
        otpEntity.setPassword(String.valueOf(otp));
        try {
            otpEntityRepository.save(otpEntity);
            LOGGER.info("{}", otpEntity);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (Boolean.TRUE.equals(sendSMS)) try {
            SMSRequestDto smsRequestDto = new SMSRequestDto(
                    "GENERAL mobile",
                    null,
                    otpEntity.getPhone(),
//                    otpEntity.getSmsText(),
                    smsText != null ? smsText : "Sizin tesdiq kodunuz " + otp,
                    null,
                    "AZERCELL"
            );
            smsService.sendSms(smsRequestDto);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        OTPResponse respBody = new OTPResponse(
                otpEntity.getId(),
                otpEntity.getModule(),
                otpEntity.getPhone(),
                String.valueOf(otp),
                sendSMS,
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond());
        return ResponseEntity.status(HttpStatus.CREATED).body(respBody);
    }

    @Override
    public ResponseEntity<CommonResponse> statusOtp(Long id) {
        String expireDuration = applicationConfiguration.getExpireDuration();
        Optional<OTPEntity> optionalOTPEntity;
        try {
            optionalOTPEntity = otpEntityRepository.findById(id);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (optionalOTPEntity.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        OTPEntity otpEntity = optionalOTPEntity.get();

        // check expiration
        if (LocalDateTime.now().minusSeconds(Long.parseLong(expireDuration)).isAfter(otpEntity.getInstanceDate())
                && otpEntity.getStatus() != 1) {
            otpEntity.setStatus(2); // 2 expired
            // update status in the DB
            otpEntityRepository.save(otpEntity);
        }
        // generate description
        Optional<OTPStatus> car = Arrays.stream(OTPStatus.values())
                .filter(c -> c.getNumber() == otpEntity.getStatus())
                .findFirst();
        String desc = car.isPresent() ? String.valueOf(car.get()) : "UNKNOWN";
        CommonResponse response = new CommonResponse(otpEntity.getStatus() + "", desc);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Object> verifyOtp(String module, String phoneNumber, String password) {
        LOGGER.info("Module : {}  MSISDN : {}  Password : {}", module, phoneNumber, password);
        if (module.equals("") || phoneNumber.equals("") || password.equals(""))
            return ResponseEntity.badRequest().build();
        OTPEntity otpEntity;

        // get OTP entity from DB
        try {
            // status 0 means waiting for response
            otpEntity = otpEntityRepository
                    .findTopByModuleEqualsAndPhoneEqualsAndStatusEqualsOrderByInstanceDateDesc(module, phoneNumber, 0)
                    .orElseThrow(() -> new NotFoundException("No such entity"));
            LOGGER.info("{}", otpEntity);
        } catch (NotFoundException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        // compare passwords
        if (otpEntity.getPassword().equals(password)) {
            // update as verified and return HttpResponse 202
            otpEntity.setStatus(1); // verified
            otpEntityRepository.save(otpEntity);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        // otherwise +1 retry count return HttpResponse 406
        otpEntity.setRetryCount(otpEntity.getRetryCount() + 1);
        if (otpEntity.getRetryCount() >= 3) {
            otpEntity.setStatus(3);
        }
        otpEntityRepository.save(otpEntity);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}

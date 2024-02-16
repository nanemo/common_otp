package com.poject.common.service.impl;

import com.poject.common.config.ApplicationConfiguration;
import com.poject.common.constants.OTPStatus;
import com.poject.common.model.dto.CommonResponse;
import com.poject.common.model.dto.OTPResponse;
import com.poject.common.model.entity.CommonOTPEntity;
import com.poject.common.model.entity.OTPCode;
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
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    public ResponseEntity<OTPResponse> generateOtp(String verificationText, Integer otpLength) {
        int otp = 0;

        if (otpLength == null) otpLength = 6;

        //CHECKING INPUT --OTP MUST BETWEEN 4 TO 9--
        if (otpLength < 4 || otpLength > 9) return ResponseEntity.badRequest().build();

        int fullDigit = (int) Math.pow(10, otpLength);
        for (int i = 1; i < fullDigit; i *= 10) {
            int random = randomObject.nextInt(9) + 1;
            otp = otp + i * random;
        }

        String otpText = String.valueOf(otp);
        String text;
        if (verificationText != null) {
            text = verificationText.replace("{otp}", otpText);
        } else {
            text = "Your verification code: " + otpText;
        }

        OTPResponse otpResponse = new OTPResponse(
                text,
                otpLength,
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime());

        LOGGER.info("OTP RESPONSE: {}", otpResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(otpResponse);
    }

    @Override
    public ResponseEntity<CommonResponse> statusOtp(Long id) {
        String expireDuration = applicationConfiguration.getExpireDuration();
        Optional<CommonOTPEntity> optionalOTPEntity;
        try {
            optionalOTPEntity = otpEntityRepository.findById(id);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (optionalOTPEntity.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        CommonOTPEntity commonOtpEntity = optionalOTPEntity.get();

        // check expiration
        List<OTPCode> otpCodeList = commonOtpEntity.getOtpCodeList();

        LocalDateTime lastOTPCodeCreatedDate =

        if (LocalDateTime.now().minusSeconds(Long.parseLong(expireDuration)).isAfter(lastOTPCodeCreatedDate)
                && commonOtpEntity.getStatus() != 1) {
            commonOtpEntity.setStatus(2); // 2 expired
            // update status in the DB
            otpEntityRepository.save(commonOtpEntity);
        }
        // generate description
        Optional<OTPStatus> car = Arrays.stream(OTPStatus.values())
                .filter(c -> c.getNumber() == commonOtpEntity.getStatus())
                .findFirst();
        String desc = car.isPresent() ? String.valueOf(car.get()) : "UNKNOWN";
        CommonResponse response = new CommonResponse(commonOtpEntity.getStatus() + "", desc);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Object> verifyOtp(String module, String phoneNumber, String password) {
        LOGGER.info("Module : {}  MSISDN : {}  Password : {}", module, phoneNumber, password);
        if (module.equals("") || phoneNumber.equals("") || password.equals(""))
            return ResponseEntity.badRequest().build();
        CommonOTPEntity commonOtpEntity;

        // get OTP entity from DB
        try {
            // status 0 means waiting for response
            commonOtpEntity = otpEntityRepository
                    .findTopByModuleEqualsAndPhoneEqualsAndStatusEqualsOrderByInstanceDateDesc(module, phoneNumber, 0)
                    .orElseThrow(() -> new NotFoundException("No such entity"));
            LOGGER.info("{}", commonOtpEntity);
        } catch (NotFoundException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        // compare passwords
        if (commonOtpEntity.getOtp().equals(password)) {
            // update as verified and return HttpResponse 202
            commonOtpEntity.setStatus(1); // verified
            otpEntityRepository.save(commonOtpEntity);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        // otherwise +1 retry count return HttpResponse 406
        commonOtpEntity.setRetryCount(commonOtpEntity.getRetryCount() + 1);
        if (commonOtpEntity.getRetryCount() >= 3) {
            commonOtpEntity.setStatus(3);
        }
        otpEntityRepository.save(commonOtpEntity);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}

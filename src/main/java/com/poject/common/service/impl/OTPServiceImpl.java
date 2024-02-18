package com.poject.common.service.impl;

import com.poject.common.config.ApplicationConfiguration;
import com.poject.common.constants.OTPStatus;
import com.poject.common.model.dto.OTPGeneratorResponse;
import com.poject.common.model.dto.OTPStatusResponse;
import com.poject.common.model.entity.OTPCommonEntity;
import com.poject.common.repository.OTPCommonRepository;
import com.poject.common.service.OTPService;
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

@Service
public class OTPServiceImpl implements OTPService {
    private static final Logger LOGGER = LoggerFactory.getLogger("businessLogger");
    private static final Random randomObject = new Random();
    private final OTPCommonRepository otpCommonRepository;
    private final ApplicationConfiguration applicationConfiguration;

    public OTPServiceImpl(OTPCommonRepository otpCommonRepository, ApplicationConfiguration applicationConfiguration) {
        this.otpCommonRepository = otpCommonRepository;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public ResponseEntity<OTPGeneratorResponse> generateOtp(String verificationText, Integer otpLength) {
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

        OTPGeneratorResponse otpGeneratorResponse = new OTPGeneratorResponse(
                text,
                otpLength,
                LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime());

        LOGGER.info("GENERATED OTP RESPONSE IS: {}", otpGeneratorResponse);

        return ResponseEntity.status(HttpStatus.CREATED).body(otpGeneratorResponse);
    }

    @Override
    public ResponseEntity<OTPStatusResponse> statusOtp(Long id) {
        String expireDuration = applicationConfiguration.getExpireDuration();


        Optional<OTPCommonEntity> optionalOTPEntity;
        try {
            optionalOTPEntity = otpCommonRepository.findById(id);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        if (optionalOTPEntity.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        OTPCommonEntity otpCommonEntity = optionalOTPEntity.get();

        // check expiration

        if (LocalDateTime.now().minusSeconds(Long.parseLong(expireDuration)).isAfter(otpCommonEntity.getCreated())
                && otpCommonEntity.getStatus() != 1) {
            otpCommonEntity.setStatus(2);
            otpCommonRepository.save(otpCommonEntity);
        }

        Optional<OTPStatus> status = Arrays.stream(OTPStatus.values())
                .filter(c -> c.getStatus() == otpCommonEntity.getStatus())
                .findFirst();

        if (status.isEmpty()) {
            throw new NullPointerException("STATUS DOES NOT EXIST! ");
        }

        String otpStatus = String.valueOf(status.get());

        return ResponseEntity.ok(new OTPStatusResponse(otpStatus));
    }

    @Override
    public ResponseEntity<Object> verifyOtp(String module, String phoneNumber, String password) {
        LOGGER.info("Module : {}  MSISDN : {}  Password : {}", module, phoneNumber, password);
        if (module.equals("") || phoneNumber.equals("") || password.equals(""))
            return ResponseEntity.badRequest().build();
        OTPCommonEntity otpCommonEntity;

        try {
            otpCommonEntity = otpCommonRepository
                    .findTopByModuleEqualsAndPhoneNumberEqualsAndStatusEqualsOrderByCreatedDesc(module, phoneNumber, 0)
                    .orElseThrow(() -> new NotFoundException("No such entity"));
            LOGGER.info("{}", otpCommonEntity);
        } catch (NotFoundException ex) {
            LOGGER.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        // compare passwords
        if (otpCommonEntity.getOtp().equals(password)) {
            // update as verified and return HttpResponse 202
            otpCommonEntity.setStatus(1); // verified
            otpCommonRepository.save(otpCommonEntity);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }
        // otherwise +1 retry count return HttpResponse 406
        otpCommonEntity.setRetryCount(otpCommonEntity.getRetryCount() + 1);
        if (otpCommonEntity.getRetryCount() >= 3) {
            otpCommonEntity.setStatus(3);
        }
        otpCommonRepository.save(otpCommonEntity);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }
}

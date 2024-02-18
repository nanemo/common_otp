package com.poject.common.service.impl;

import com.poject.common.config.ApplicationConfiguration;
import com.poject.common.model.dto.OTPRequest;
import com.poject.common.model.dto.VerificationResponse;
import com.poject.common.model.entity.OTPCommonEntity;
import com.poject.common.model.map.CommonOTPEntityMapper;
import com.poject.common.repository.OTPCommonRepository;
import com.poject.common.service.OTPService;
import com.poject.common.service.SMSService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SMSServiceImpl implements SMSService {
    private static final Logger LOGGER = LoggerFactory.getLogger("businessLogger");
    private final RestTemplate restTemplate;
    private final ApplicationConfiguration applicationConfiguration;
    private final OTPService otpService;
    private final OTPCommonRepository otpCommonRepository;
    private final CommonOTPEntityMapper commonOTPEntityMapper;


    @Override
    public ResponseEntity<VerificationResponse> sendSMSVerification(OTPRequest otpRequest, Integer otpLength) {
        try {
            otpRequest.setLogin(applicationConfiguration.getSmsLogin());
            otpRequest.setKey(applicationConfiguration.getSmsKey());
            String urlSmsServiceSend = applicationConfiguration.getSmsServiceUrl();

            LOGGER.info("SEND SMS INPUT: {}", otpRequest);

            ResponseEntity<VerificationResponse> verificationResponse =
                    restTemplate.postForEntity(urlSmsServiceSend, otpRequest, VerificationResponse.class);

            LOGGER.info("SENT SMS : {}", verificationResponse);

            OTPCommonEntity otpCommonEntity = commonOTPEntityMapper.smsRequestDtoToCommonOTPEntity(otpRequest);

            otpCommonRepository.save(otpCommonEntity);
            LOGGER.info("SAVED COMMON OTP ENTITY {}", otpCommonEntity);

            return verificationResponse;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return ResponseEntity.internalServerError().body(
                    new VerificationResponse(null,
                            "Something went wrong",
                            null,
                            500));
        }
    }

}

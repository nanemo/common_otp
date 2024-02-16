package com.poject.common.service.impl;

import com.poject.common.config.ApplicationConfiguration;
import com.poject.common.model.dto.OTPResponse;
import com.poject.common.model.dto.SMSRequestDto;
import com.poject.common.model.dto.SMSResponseDto;
import com.poject.common.model.entity.CommonOTPEntity;
import com.poject.common.model.map.CommonOTPEntityMapper;
import com.poject.common.repository.OTPEntityRepository;
import com.poject.common.service.OTPService;
import com.poject.common.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SmsServiceImpl implements SmsService {
    private static final Logger LOGGER = LoggerFactory.getLogger("businessLogger");
    private final RestTemplate restTemplate;
    private final ApplicationConfiguration applicationConfiguration;
    private final OTPService otpService;
    private final OTPEntityRepository otpEntityRepository;
    private final CommonOTPEntityMapper commonOTPEntityMapper;

    public ResponseEntity<SMSResponseDto> sendSMSVerification(SMSRequestDto smsRequestDto, Integer otpLength) {
        ResponseEntity<OTPResponse> otpResponseEntity = otpService.generateOtp(smsRequestDto.getText(), otpLength);

        return sendSms(smsRequestDto);
    }

    @Override
    @Transactional
    //TODO If we will need send SMS api for general SMSes than I will separate it.
    public ResponseEntity<SMSResponseDto> sendSms(SMSRequestDto smsRequestDto) {

        smsRequestDto.setLogin(applicationConfiguration.getSmsLogin());
        smsRequestDto.setKey(applicationConfiguration.getSmsKey());

        try {
            LOGGER.info("SEND SMS INPUT: {}", smsRequestDto);
            String urlSmsServiceSend = applicationConfiguration.getSmsServiceUrl();
            ResponseEntity<SMSResponseDto> smsResponseDto =
                    restTemplate.postForEntity(urlSmsServiceSend, smsRequestDto, SMSResponseDto.class);
            LOGGER.info("SENT SMS : {}", smsResponseDto);

            CommonOTPEntity commonOTPEntity = commonOTPEntityMapper.smsRequestDtoToCommonOTPEntity(smsRequestDto);

            otpEntityRepository.save(commonOTPEntity);
            LOGGER.info("SAVED COMMON OTP ENTITY {}", commonOTPEntity);

            return smsResponseDto;
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

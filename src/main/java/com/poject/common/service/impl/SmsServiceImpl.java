package com.poject.common.service.impl;

import com.poject.common.config.ApplicationConfiguration;
import com.poject.common.model.dto.SMSRequestDto;
import com.poject.common.model.dto.SMSResponseDto;
import com.poject.common.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsServiceImpl implements SmsService {
    private static final Logger LOGGER = LoggerFactory.getLogger("businessLogger");
    private final RestTemplate restTemplate;
    private final ApplicationConfiguration applicationConfiguration;

    public SmsServiceImpl(RestTemplate restTemplate, ApplicationConfiguration applicationConfiguration) {
        this.restTemplate = restTemplate;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public SMSResponseDto sendSms(SMSRequestDto smsRequestDto) {
        LOGGER.info("SEND SMS INPUT: {}", smsRequestDto);
        String urlSmsServiceSend = applicationConfiguration.getUrlSmsServiceSend();
        ResponseEntity<SMSResponseDto> smsResponseEntity =
                restTemplate.postForEntity(urlSmsServiceSend, smsRequestDto, SMSResponseDto.class);
        LOGGER.info("SMS sent : {}", smsResponseEntity);
        return smsResponseEntity.getBody();
    }
}

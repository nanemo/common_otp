package com.poject.common.service;

import com.poject.common.model.dto.SMSRequestDto;
import com.poject.common.model.dto.SMSResponseDto;
import org.springframework.http.ResponseEntity;

public interface SmsService {
    ResponseEntity<SMSResponseDto> sendSMSVerification(SMSRequestDto smsRequestDto, Integer otpLength);

    ResponseEntity<SMSResponseDto> sendSms(SMSRequestDto smsRequestDto);
}

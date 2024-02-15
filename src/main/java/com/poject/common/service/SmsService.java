package com.poject.common.service;

import com.poject.common.model.dto.SMSRequestDto;
import com.poject.common.model.dto.SMSResponseDto;

public interface SmsService {
    SMSResponseDto sendSms(SMSRequestDto smsRequestDto);
}

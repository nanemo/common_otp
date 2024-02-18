package com.poject.common.service;

import com.poject.common.model.dto.OTPRequest;
import com.poject.common.model.dto.VerificationResponse;
import org.springframework.http.ResponseEntity;

public interface OTPSenderService {
    ResponseEntity<VerificationResponse> otpSender(OTPRequest mailRequestDto, Integer length);
}

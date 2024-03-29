package com.poject.common.service;

import com.poject.common.model.dto.OTPRequest;
import com.poject.common.model.dto.VerificationResponse;
import org.springframework.http.ResponseEntity;

public interface MailService {
    public ResponseEntity<VerificationResponse> sendMailVerification(OTPRequest otpRequest);
}

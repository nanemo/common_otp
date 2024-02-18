package com.poject.common.service;

import com.poject.common.model.dto.OTPRequest;
import com.poject.common.model.dto.VerificationResponse;
import org.springframework.http.ResponseEntity;

public interface SMSService {
    ResponseEntity<VerificationResponse> sendSMSVerification(OTPRequest OTPRequest, Integer otpLength);

}

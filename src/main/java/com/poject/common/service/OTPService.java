package com.poject.common.service;

import com.poject.common.model.dto.OTPGeneratorResponse;
import com.poject.common.model.dto.OTPStatusResponse;
import org.springframework.http.ResponseEntity;

public interface OTPService {
    ResponseEntity<OTPGeneratorResponse> generateOtp(String verificationText, Integer otpLength);

    ResponseEntity<OTPStatusResponse> statusOtp(Long id);

    ResponseEntity<Object> verifyOtp(String module, String phoneNumber, String password);
}

package com.poject.common.service;

import com.poject.common.model.dto.CommonResponse;
import com.poject.common.model.dto.MailRequestDto;
import com.poject.common.model.dto.OTPResponse;
import org.springframework.http.ResponseEntity;

public interface OTPService {
    ResponseEntity<OTPResponse> generateOtp(String verificationText, Integer otpLength);

    ResponseEntity<CommonResponse> statusOtp(Long id);

    ResponseEntity<Object> verifyOtp(String module, String phoneNumber, String password);
}

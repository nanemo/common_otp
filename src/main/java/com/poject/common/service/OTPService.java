package com.poject.common.service;

import com.poject.common.model.dto.CommonResponse;
import com.poject.common.model.dto.OTPResponse;
import com.poject.common.model.entity.OTPEntity;
import org.springframework.http.ResponseEntity;

public interface OTPService {
    ResponseEntity<OTPResponse> generateOtp(Integer length, String send, OTPEntity otpEntity);

    ResponseEntity<CommonResponse> statusOtp(Long id);

    ResponseEntity<Object> verifyOtp(String module, String phoneNumber, String password);
}

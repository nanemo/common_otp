package com.poject.common.controller;

import com.poject.common.model.dto.OTPRequest;
import com.poject.common.model.dto.VerificationResponse;
import com.poject.common.service.OTPSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/mail/verification")
public class OTPSenderController {
    private final OTPSenderService OTPSenderService;

    @PostMapping("/send/{otp_length}")
    public ResponseEntity<VerificationResponse> sendMailVerification(@RequestBody OTPRequest otpRequest,
                                                                     @PathVariable("otp_length") Integer otpLength) {
            return OTPSenderService.otpSender(otpRequest, otpLength);
    }

}

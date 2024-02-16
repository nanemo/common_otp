package com.poject.common.controller;

import com.poject.common.model.dto.SMSRequestDto;
import com.poject.common.model.dto.SMSResponseDto;
import com.poject.common.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms/verification")
public class SmsVerificationController {
    private final SmsService smsService;

    public SmsVerificationController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send/{opt_length}")
    public ResponseEntity<SMSResponseDto> sendSmsVerification(@RequestBody SMSRequestDto smsRequestDto,
                                                              @PathVariable(name = "opt_length") Integer otpLength) {
        //TODO öz clientimizə qaytarılan SMSResponseDTO-nun içi LSimin qaytardığı responsdur.
        // Lazım map edərəm istədiyimiz Responsa
        return smsService.sendSMSVerification(smsRequestDto, otpLength);
    }

}

package com.poject.common.controller;

import com.poject.common.model.dto.CommonResponse;
import com.poject.common.model.dto.MailRequestDto;
import com.poject.common.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/mail/verification")
public class MailVerificationController {
    private final MailService mailService;

    public MailVerificationController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send/{otp_length}")
    public ResponseEntity<CommonResponse> sendMailVerification(@RequestBody MailRequestDto mailRequestDto,
                                                               @PathVariable("otp_length") Integer otpLength) {
        return mailService.sendMailVerification(mailRequestDto, otpLength);
    }
}

package com.poject.common.service.impl;

import com.poject.common.model.dto.OTPRequest;
import com.poject.common.model.dto.VerificationResponse;
import com.poject.common.service.MailService;
import com.poject.common.service.OTPSenderService;
import com.poject.common.service.OTPService;
import com.poject.common.service.SMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OTPSenderServiceImpl implements OTPSenderService {
    private final OTPService otpService;
    private final MailService mailService;
    private final SMSService smsService;

    @Override
    public ResponseEntity<VerificationResponse> otpSender(OTPRequest otpRequest, Integer length) {
        String otpText = otpService.generateOtp(otpRequest.getText(), length).getBody().getOtp();
        otpRequest.setText(otpText);

        if (otpRequest.getReceiver().contains("@")) {
            return mailService.sendMailVerification(otpRequest);
        }

        return smsService.sendSMSVerification(otpRequest, length);
    }



}

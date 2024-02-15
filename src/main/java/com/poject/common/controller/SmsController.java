package com.poject.common.controller;

import com.poject.common.model.dto.SMSRequestDto;
import com.poject.common.model.dto.SMSResponseDto;
import com.poject.common.service.SmsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sms")
public class SmsController {
    private final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("send")
    public SMSResponseDto sendSms(@RequestBody SMSRequestDto smsRequestDto) {
        return smsService.sendSms(smsRequestDto);
    }

}

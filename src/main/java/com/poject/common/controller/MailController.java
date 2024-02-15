package com.poject.common.controller;

import com.poject.common.model.dto.CommonResponse;
import com.poject.common.model.dto.MailInputDto;
import com.poject.common.service.MailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @PostMapping("/send")
    public ResponseEntity<CommonResponse> sendEmail(@RequestBody MailInputDto mailInputDto) {
        return mailService.sendMail(mailInputDto);
    }
}

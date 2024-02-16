package com.poject.common.service;

import com.poject.common.model.dto.CommonResponse;
import com.poject.common.model.dto.MailRequestDto;
import org.springframework.http.ResponseEntity;

public interface MailService {
    ResponseEntity<CommonResponse> sendMailVerification(MailRequestDto mailRequestDto, Integer length);
    ResponseEntity<CommonResponse> sendMail(MailRequestDto mailRequestDto);
}

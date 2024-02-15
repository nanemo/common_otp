package com.poject.common.service;

import com.poject.common.model.dto.CommonResponse;
import com.poject.common.model.dto.MailInputDto;
import org.springframework.http.ResponseEntity;

public interface MailService {
    ResponseEntity<CommonResponse> sendMail(MailInputDto mailInputDto);
}

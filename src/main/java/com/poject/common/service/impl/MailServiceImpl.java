package com.poject.common.service.impl;

import com.poject.common.config.ApplicationConfiguration;
import com.poject.common.model.dto.CommonResponse;
import com.poject.common.model.dto.MailRequestDto;
import com.poject.common.model.dto.OTPResponse;
import com.poject.common.service.MailService;
import com.poject.common.service.OTPService;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;

@Service
public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger("businessLogger");

    private JavaMailSender javaMailSender;
    private final OTPService otpService;

    public MailServiceImpl(JavaMailSender javaMailSender, OTPService otpService) {
        this.javaMailSender = javaMailSender;
        this.otpService = otpService;
    }

    @Override
    public ResponseEntity<CommonResponse> sendMailVerification(MailRequestDto mailRequestDto, Integer length) {
        ResponseEntity<OTPResponse> otpResponseEntity = otpService.generateOtp(mailRequestDto.getContent(), length);

        return sendMail(mailRequestDto);
    }

    @Override
    public ResponseEntity<CommonResponse> sendMail(MailRequestDto mailRequestDto) {
        LOGGER.info("SENDING MAIL INPUT : {}", mailRequestDto);
        try {
            String emailTo = Arrays.toString(mailRequestDto.getEmailTo());
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            if (mailRequestDto.getAttachmentBase64() != null) {

                String fileType = mailRequestDto.getAttachmentName().split("\\.")[1];

                if (ApplicationConfiguration.mimeType.containsKey(fileType)) {
                    String mimeValue = ApplicationConfiguration.mimeType.get(fileType);
                    MimeBodyPart content = new MimeBodyPart();
                    content.setText(mailRequestDto.getContent());

                    MimeBodyPart mimeAttachment = new MimeBodyPart();
                    mimeAttachment.setFileName(mailRequestDto.getAttachmentName());
                    // Attach the file to email
                    mimeAttachment.setContent(Base64.getDecoder().decode(mailRequestDto.getAttachmentBase64()), mimeValue);

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(content);
                    multipart.addBodyPart(mimeAttachment);
                    msg.setContent(multipart);
                } else {
                    helper.addAttachment(mailRequestDto.getAttachmentName(),
                            new ByteArrayResource(Base64.getDecoder().decode(mailRequestDto.getAttachmentBase64())));
                }
            }
            helper.setTo(mailRequestDto.getEmailTo());
            if (mailRequestDto.getBcc() != null)
                helper.setBcc(mailRequestDto.getBcc());
            if (mailRequestDto.getCc() != null)
                helper.setCc(mailRequestDto.getCc());
            helper.setFrom("nanemodeveloper@gmail.com");
            helper.setSubject(mailRequestDto.getSubject());
            helper.setText(mailRequestDto.getContent());
            javaMailSender.send(msg);
            LOGGER.info("EMAIL SUCCESSFULLY SENT TO : {}", emailTo);
        } catch (MessagingException exception) {
            LOGGER.error(ExceptionUtils.getStackTrace(exception), exception);
            return ResponseEntity
                    .status(500)
                    .body(new CommonResponse(
                            "500",
                            "Mail Göndərilən zaman bilinməyən xəta baş verdi"));
        }

        return ResponseEntity
                .ok(new CommonResponse(
                        "0",
                        "Successfully Proceed"));
    }
}

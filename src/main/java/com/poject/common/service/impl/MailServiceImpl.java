package com.poject.common.service.impl;

import com.poject.common.config.ApplicationConfiguration;
import com.poject.common.model.dto.CommonResponse;
import com.poject.common.model.dto.MailInputDto;
import com.poject.common.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public ResponseEntity<CommonResponse> sendMail(MailInputDto mailInputDto) {
        LOGGER.info("SENDING MAIL INPUT : {}", mailInputDto);
        try {
            String emailTo = Arrays.toString(mailInputDto.getEmailTo());
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);

            if (mailInputDto.getAttachmentBase64() != null) {

                String fileType = mailInputDto.getAttachmentName().split("\\.")[1];

                if (ApplicationConfiguration.mimeType.containsKey(fileType)) {
                    String mimeValue = ApplicationConfiguration.mimeType.get(fileType);
                    MimeBodyPart content = new MimeBodyPart();
                    content.setText(mailInputDto.getContent());

                    MimeBodyPart mimeAttachment = new MimeBodyPart();
                    mimeAttachment.setFileName(mailInputDto.getAttachmentName());
                    // Attach the file to email
                    mimeAttachment.setContent(Base64.getDecoder().decode(mailInputDto.getAttachmentBase64()), mimeValue);

                    Multipart multipart = new MimeMultipart();
                    multipart.addBodyPart(content);
                    multipart.addBodyPart(mimeAttachment);
                    msg.setContent(multipart);
                } else {
                    helper.addAttachment(mailInputDto.getAttachmentName(),
                            new ByteArrayResource(Base64.getDecoder().decode(mailInputDto.getAttachmentBase64())));
                }
            }
            helper.setTo(mailInputDto.getEmailTo());
            if (mailInputDto.getBcc() != null)
                helper.setBcc(mailInputDto.getBcc());
            if (mailInputDto.getCc() != null)
                helper.setCc(mailInputDto.getCc());
            helper.setFrom("nanemodeveloper@gmail.com");
            helper.setSubject(mailInputDto.getSubject());
            helper.setText(mailInputDto.getContent());
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

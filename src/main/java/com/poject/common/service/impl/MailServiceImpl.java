package com.poject.common.service.impl;

import com.poject.common.exception.GlobalExceptionHandler;
import com.poject.common.model.dto.OTPRequest;
import com.poject.common.model.dto.VerificationResponse;
import com.poject.common.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger("businessLogger");

    private final JavaMailSender javaMailSender;
    private final GlobalExceptionHandler globalExceptionHandler;

    public ResponseEntity<VerificationResponse> sendMailVerification(OTPRequest otpRequest) {
        LOGGER.info("SENDER INPUT : {}", otpRequest);
        try {
            String emailTo = otpRequest.getReceiver();

            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            MimeBodyPart content = new MimeBodyPart();
            content.setText(otpRequest.getText());

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(content);
            msg.setContent(multipart);

            helper.setTo(otpRequest.getReceiver());
            helper.setFrom("nanemodeveloper@gmail.com");
            helper.setSubject(otpRequest.getSender());
            helper.setText(otpRequest.getText());
            javaMailSender.send(msg);

            LOGGER.info("EMAIL SUCCESSFULLY SENT TO : {}", emailTo);

        } catch (MessagingException exception) {
            LOGGER.error(exception.getMessage(), exception);
            globalExceptionHandler.handleExceptions(exception);
        }
        return ResponseEntity.ok(
                new VerificationResponse("VERIFICATION WAS SENT SUCCESSFULLY",
                        null,
                        null,
                        200));
    }

}

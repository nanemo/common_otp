package com.poject.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ApplicationConfiguration {
    public static final Map<String, String> mimeType = new HashMap<>();

    @Value("${sms.service.url}")
    private String smsServiceUrl;
    @Value("${sms.service.login")
    private String smsLogin;
    @Value("${sms.service.key")
    private String smsKey;
    @Value("${otp.expire.duration}")
    private String expireDuration;
    @Value("${db.file.path}")
    private String vaultDBUrl;
    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.port}")
    private int mailPort;
    @Value("${spring.mail.username}")
    private String mailUsername;
    @Value("${spring.mail.password}")
    private String mailPassword;

    @Bean
    public static Map<String, String> setMimeType() {
        mimeType.put(".avi", "video/x-msvideo");
        mimeType.put(".csv", "text/csv");
        mimeType.put(".doc", "application/msword");
        mimeType.put(".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mimeType.put(".gif", "image/gif");
        mimeType.put(".html", "text/html");
        mimeType.put(".jpeg", "image/jpeg");
        mimeType.put(".jpg", "image/jpeg");
        mimeType.put(".json", "application/json");
        mimeType.put(".mp3", "audio/mpeg");
        mimeType.put(".mp4", "video/mp4");
        mimeType.put(".png", "image/png");
        mimeType.put(".pdf", "application/pdf");
        mimeType.put(".xls", "application/vnd.ms-excel");
        mimeType.put(".xml", "application/xml");
        return mimeType;
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() throws IOException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);
        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public String getVaultDBUrl() {
        return vaultDBUrl;
    }

    public ApplicationConfiguration setVaultDBUrl(String vaultDBUrl) {
        this.vaultDBUrl = vaultDBUrl;
        return this;
    }

    public String getExpireDuration() {
        return expireDuration;
    }


    public String setExpireDuration(String expireDuration) {
        this.expireDuration = expireDuration;
        return this.expireDuration;
    }

    public String getSmsServiceUrl() {
        return smsServiceUrl;
    }


    public String setSmsServiceUrl(String smsServiceUrl) {
        this.smsServiceUrl = smsServiceUrl;
        return smsServiceUrl;
    }

    public String getSmsLogin() {
        return smsLogin;
    }

    public void setSmsLogin(String smsLogin) {
        this.smsLogin = smsLogin;
    }

    public String getSmsKey() {
        return smsKey;
    }

}

package com.poject.common.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.poject.common.vault.Config;
import com.poject.common.vault.exchange.ExchangeData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ApplicationConfiguration {
    public static final ObjectMapper jsonMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    public static final Map<String, String> mimeType = new HashMap<>();

    private String urlSmsServiceSend;
    private String expireDuration;
    private String vaultDBUrl;
    private String vaultExchangeUrl;

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

    public String getVaultExchangeUrl() {
        return vaultExchangeUrl;
    }

    @Value("${exchange.file.path:/default/exchange/path}")
    public ApplicationConfiguration setVaultExchangeUrl(String vaultExchangeUrl) {
        this.vaultExchangeUrl = vaultExchangeUrl;
        return this;
    }

    @Bean
    @Lazy
    public JavaMailSenderImpl javaMailSender() throws IOException {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        TypeReference<Config<ExchangeData>> reference = new TypeReference<>() {};
        Config<ExchangeData> databaseConfig = jsonMapper.readValue(new File(vaultExchangeUrl), reference);
        ExchangeData data = databaseConfig.getData().getData();
        javaMailSender.setPassword(data.getMailPassword()); //data.getMailPassword()
        return javaMailSender;
    }

    public String getVaultDBUrl() {
        return vaultDBUrl;
    }

    @Value("${db.file.path}")
    public ApplicationConfiguration setVaultDBUrl(String vaultDBUrl) {
        this.vaultDBUrl = vaultDBUrl;
        return this;
    }

    public String getExpireDuration() {
        return expireDuration;
    }

    @Value("${otp.expire.duration}")
    public void setExpireDuration(String expireDuration) {
        this.expireDuration = expireDuration;
    }

    public String getUrlSmsServiceSend() {
        return urlSmsServiceSend;
    }

    @Value("${url.sms.service.send}")
    public void setUrlSmsServiceSend(String urlSmsServiceSend) {
        this.urlSmsServiceSend = urlSmsServiceSend;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

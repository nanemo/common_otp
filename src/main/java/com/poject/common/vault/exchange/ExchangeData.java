package com.poject.common.vault.exchange;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Value;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeData {

    @JsonProperty("mail.password")
    @Value("${spring.mail.password}")
    private String mailPassword;

    public String getMailPassword() {
        return mailPassword;
    }

    public ExchangeData setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
        return this;
    }
}

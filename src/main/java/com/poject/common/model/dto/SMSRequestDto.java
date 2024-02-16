package com.poject.common.model.dto;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public class SMSRequestDto implements Serializable {

    private static final long serialVersionUID = -3924046329982906718L;

    private String login; // from property file
    private String key; // from property file
    private String phoneNumber;
    private String text;
    private String sender;
    private LocalDateTime scheduled;
    private Boolean unicode;

    /**
     * No args constructor for use in serialization
     */
    public SMSRequestDto() {
    }

    public SMSRequestDto(String login, String key, String phoneNumber, String text, String sender, LocalDateTime scheduled, Boolean unicode) {
        this.login = login;
        this.key = key;
        this.phoneNumber = phoneNumber;
        this.text = text;
        this.sender = sender;
        this.scheduled = scheduled;
        this.unicode = unicode;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getScheduled() {
        return scheduled;
    }

    public void setScheduled(LocalDateTime scheduled) {
        this.scheduled = scheduled;
    }

    public Boolean getUnicode() {
        return unicode;
    }

    public void setUnicode(Boolean unicode) {
        this.unicode = unicode;
    }

    @Override
    public String toString() {
        return "SMSRequestDto{" +
                "login='" + login + '\'' +
                ", key='" + key + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", text='" + text + '\'' +
                ", sender='" + sender + '\'' +
                ", scheduled=" + scheduled +
                ", unicode=" + unicode +
                '}';
    }
}
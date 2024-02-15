package com.poject.common.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OTPResponse implements Serializable {

    private Long id;
    private String module;
    private String msisdn;
    @JsonProperty("OTP")
    private String otp;
    private Boolean sendSMS;
    private Long timestamp;

    public OTPResponse(Long id, String module, String msisdn, String otp, Boolean sendSMS, Long timestamp) {
        this.id = id;
        this.module = module;
        this.msisdn = msisdn;
        this.otp = otp;
        this.sendSMS = sendSMS;
        this.timestamp = timestamp;
    }

    public OTPResponse(String module, String msisdn, String otp, Boolean sendSMS, Long timestamp) {
        this.module = module;
        this.msisdn = msisdn;
        this.otp = otp;
        this.sendSMS = sendSMS;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public OTPResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public String getModule() {
        return module;
    }

    public OTPResponse setModule(String module) {
        this.module = module;
        return this;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public OTPResponse setMsisdn(String msisdn) {
        this.msisdn = msisdn;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public OTPResponse setOtp(String otp) {
        this.otp = otp;
        return this;
    }

    public Boolean getSendSMS() {
        return sendSMS;
    }

    public OTPResponse setSendSMS(Boolean sendSMS) {
        this.sendSMS = sendSMS;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public OTPResponse setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}

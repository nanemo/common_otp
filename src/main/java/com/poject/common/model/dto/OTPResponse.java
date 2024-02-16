package com.poject.common.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OTPResponse implements Serializable {
    private String otp;
    private Integer length;
    private LocalDateTime created;

    public OTPResponse(String otp, Integer length, LocalDateTime created) {
        this.otp = otp;
        this.length = length;
        this.created = created;
    }

    public OTPResponse() {
    }

    public Integer getLength() {
        return length;
    }

    public Integer setLength(Integer length) {
        this.length = length;
        return this.length;
    }

    public String getOtp() {
        return otp;
    }

    public String setOtp(String otp) {
        this.otp = otp;
        return this.otp;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime setTimestamp(LocalDateTime timestamp) {
        this.created = timestamp;
        return this.created;
    }

    @Override
    public String toString() {
        return "OTPResponse{" +
                "otp='" + otp + '\'' +
                ", length=" + length +
                ", created=" + created +
                '}';
    }
}

package com.poject.common.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "common_otp")
public class OTPEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_id")
    private Long id;

    @Column(name = "module")
    private String module;


    @Column(name = "phone_number")
    private String phone;


    @Column(name = "sms_text")
    private String smsText;


    @Column(name = "password")
    private String password;


    @Column(name = "instance_date")
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // json formatter and parser
    private LocalDateTime instanceDate;

    @Column(name = "retry_count")
    private int retryCount;
    @Column(name = "status")
    private int status;

    public Long getId() {
        return id;
    }

    public OTPEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getModule() {
        return module;
    }

    public OTPEntity setModule(String module) {
        this.module = module;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public OTPEntity setPhone(String msisdn) {
        this.phone = msisdn;
        return this;
    }

    public String getSmsText() {
        return smsText;
    }

    public OTPEntity setSmsText(String smsText) {
        this.smsText = smsText;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public OTPEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public LocalDateTime getInstanceDate() {
        return instanceDate;
    }

    public OTPEntity setInstanceDate(LocalDateTime createdAt) {
        this.instanceDate = createdAt;
        return this;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public OTPEntity setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public OTPEntity setStatus(int status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "OTPEntity{" +
                "id=" + id +
                ", module='" + module + '\'' +
                ", phone='" + phone + '\'' +
                ", smsText='" + smsText + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + instanceDate +
                ", retryCount=" + retryCount +
                ", status=" + status +
                '}';
    }
}
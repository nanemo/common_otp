package com.poject.common.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "otp_code")
public class OTPCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "otp_code")
    private String otpCode;

    private String text;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "status")
    private int status;

}

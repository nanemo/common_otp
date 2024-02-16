package com.poject.common.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "common_otp")
public class CommonOTPEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String module;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "common_otp_id")
    private List<OTPCode> otpCodeList;

    @Column(name = "retry_count")
    private int retryCount;


}
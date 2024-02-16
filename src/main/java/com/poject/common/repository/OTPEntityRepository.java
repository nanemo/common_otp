package com.poject.common.repository;

import com.poject.common.model.entity.CommonOTPEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPEntityRepository extends JpaRepository<CommonOTPEntity, Long> {
    Optional<CommonOTPEntity> findTopByModuleEqualsAndPhoneEqualsAndStatusEqualsOrderByInstanceDateDesc(String module, String phone, int status);
}

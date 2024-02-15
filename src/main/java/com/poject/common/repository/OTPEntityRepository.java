package com.poject.common.repository;

import com.poject.common.model.entity.OTPEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPEntityRepository extends JpaRepository<OTPEntity, Long> {
    Optional<OTPEntity> findTopByModuleEqualsAndPhoneEqualsAndStatusEqualsOrderByInstanceDateDesc(String module, String phone, int status);
}

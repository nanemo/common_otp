package com.poject.common.repository;

import com.poject.common.model.entity.OTPCommonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPCommonRepository extends JpaRepository<OTPCommonEntity, Long> {
    Optional<OTPCommonEntity> findTopByModuleEqualsAndPhoneNumberEqualsAndStatusEqualsOrderByCreatedDesc(String module, String phone, int status);
}

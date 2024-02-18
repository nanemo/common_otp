package com.poject.common.model.map;

import com.poject.common.model.dto.OTPRequest;
import com.poject.common.model.entity.OTPCommonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommonOTPEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "phoneNumber", source = "receiver")
    @Mapping(target = "email", source = "receiver")
    @Mapping(target = "retryCount", ignore = true) // Bura count elave ele
    @Mapping(target = "otp", source = "text")
    @Mapping(target = "created", source = "scheduled")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "text", ignore = true)
    OTPCommonEntity smsRequestDtoToCommonOTPEntity(OTPRequest otpRequest);

}

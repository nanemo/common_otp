package com.poject.common.model.map;

import com.poject.common.constants.OTPStatus;
import com.poject.common.model.dto.SMSRequestDto;
import com.poject.common.model.entity.CommonOTPEntity;
import com.poject.common.model.entity.OTPCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CommonOTPEntityMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "otpCodeList", expression = ("java(addOTPCodeToList(smsRequestDto))"))
    @Mapping(target = "retryCount", ignore = true) // Bura count elave ele
    CommonOTPEntity smsRequestDtoToCommonOTPEntity(SMSRequestDto smsRequestDto);

    default List<OTPCode> addOTPCodeToList(SMSRequestDto smsRequestDto, String otp) {
        ArrayList<OTPCode> otpCodes = new ArrayList<>();
        OTPCode e = new OTPCode();
        e.setOtpCode(otp);
        e.setText(smsRequestDto.getText());
        e.setCreatedDate(LocalDateTime.now());
        e.setStatus(OTPStatus.WAITING.getNumber());

        otpCodes.add(e);
        return otpCodes;
    }
}

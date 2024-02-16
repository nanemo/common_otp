package com.poject.common.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("jsonschema2pojo")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SMSResponseDto implements Serializable {

    private static final long serialVersionUID = 3130932563194348607L;


    private String successMessage;
    private String errorMessage;
    private Integer transactionId;
    private Integer errorCode;

    /**
     * No args constructor for use in serialization
     */


}
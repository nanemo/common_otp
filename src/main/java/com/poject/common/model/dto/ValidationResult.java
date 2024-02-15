package com.poject.common.model.dto;

import java.io.Serializable;

public class ValidationResult implements Serializable {
    private Integer code;
    private String message;

    public ValidationResult() {
    }

    public ValidationResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public Integer getCode() {
        return code;
    }

    public ValidationResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ValidationResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public static ValidationResult badRequest() {
        return new ValidationResult(400, "Json düzgün göndərilməmişdir");
    }

    public static ValidationResult internalError() {
        return new ValidationResult(500, "Internal Error");
    }
}

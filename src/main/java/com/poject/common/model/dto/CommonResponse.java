package com.poject.common.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "description"
})
public class CommonResponse implements Serializable {

    private static final long serialVersionUID = 5609035215176888461L;
    @JsonProperty("code")
    private String code;
    @JsonProperty("description")
    private String description;

    /**
     * No args constructor for use in serialization
     */
    public CommonResponse() {
    }


    public CommonResponse(String code, String description) {
        super();
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public CommonResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CommonResponse setDescription(String description) {
        this.description = description;
        return this;
    }
}
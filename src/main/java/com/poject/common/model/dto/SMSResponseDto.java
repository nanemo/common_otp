package com.poject.common.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.annotation.Generated;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Application",
        "MessageID",
        "Code",
        "Description"
})
@Generated("jsonschema2pojo")
public class SMSResponseDto implements Serializable {

    private final static long serialVersionUID = 3130932563194348607L;
    @JsonProperty("Application")
    private String application;
    @JsonProperty("MessageID")
    private Long messageID;
    @JsonProperty("Code")
    private String code;
    @JsonProperty("Description")
    private String description;

    /**
     * No args constructor for use in serialization
     */
    public SMSResponseDto() {
    }

    /**
     * @param code
     * @param application
     * @param messageID
     * @param description
     */
    public SMSResponseDto(String application, Long messageID, String code, String description) {
        super();
        this.application = application;
        this.messageID = messageID;
        this.code = code;
        this.description = description;
    }

    @JsonProperty("Application")
    public String getApplication() {
        return application;
    }

    @JsonProperty("Application")
    public void setApplication(String application) {
        this.application = application;
    }

    @JsonProperty("MessageID")
    public Long getMessageID() {
        return messageID;
    }

    @JsonProperty("MessageID")
    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    @JsonProperty("Code")
    public String getCode() {
        return code;
    }

    @JsonProperty("Code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("Description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("Description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SMSResponseDto{" +
                "application='" + application + '\'' +
                ", messageID=" + messageID +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
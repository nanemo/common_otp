package com.poject.common.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "RequestedBy",
        "MessageId",
        "PhoneNumber",
        "Message",
        "ScheduleDate",
        "Operator"
})
public class SMSRequestDto implements Serializable {

    private static final long serialVersionUID = -3924046329982906718L;
    @JsonProperty("RequestedBy")
    private String requestedBy;
    @JsonProperty("MessageId")
    private Object messageId;
    @JsonProperty("PhoneNumber")
    private String phoneNumber;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("ScheduleDate")
    private Object scheduleDate;
    @JsonProperty("Operator")
    private String operator;

    /**
     * No args constructor for use in serialization
     */
    public SMSRequestDto() {
    }


    public SMSRequestDto(String requestedBy, Object messageId, String phoneNumber, String message, Object scheduleDate, String operator) {
        super();
        this.requestedBy = requestedBy;
        this.messageId = messageId;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.scheduleDate = scheduleDate;
        this.operator = operator;
    }

    @JsonProperty("RequestedBy")
    public String getRequestedBy() {
        return requestedBy;
    }

    @JsonProperty("RequestedBy")
    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    @JsonProperty("MessageId")
    public Object getMessageId() {
        return messageId;
    }

    @JsonProperty("MessageId")
    public void setMessageId(Object messageId) {
        this.messageId = messageId;
    }

    @JsonProperty("PhoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("PhoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("Message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("Message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("ScheduleDate")
    public Object getScheduleDate() {
        return scheduleDate;
    }

    @JsonProperty("ScheduleDate")
    public void setScheduleDate(Object scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    @JsonProperty("Operator")
    public String getOperator() {
        return operator;
    }

    @JsonProperty("Operator")
    public void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "SMSRequestDto{" +
                "requestedBy='" + requestedBy + '\'' +
                ", messageId=" + messageId +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                ", scheduleDate=" + scheduleDate +
                ", operator='" + operator + '\'' +
                '}';
    }
}
package com.poject.common.vault;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "request_id",
        "lease_id",
        "lease_duration",
        "renewable",
        "data",
        "warnings"
})
public class Config<T> implements Serializable {

    private static final long serialVersionUID = -531053223700378805L;
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("lease_id")
    private String leaseId;
    @JsonProperty("lease_duration")
    private Integer leaseDuration;
    @JsonProperty("renewable")
    private Boolean renewable;
    @JsonProperty("data")
    private Data<T> data;
    @JsonProperty("warnings")
    private String warnings;

    /**
     * No args constructor for use in serialization
     */
    public Config() {
    }

    /**
     * @param leaseId
     * @param data
     * @param requestId
     * @param renewable
     * @param warnings
     * @param leaseDuration
     */
    public Config(String requestId, String leaseId, Integer leaseDuration, Boolean renewable, Data<T> data, String warnings) {
        super();
        this.requestId = requestId;
        this.leaseId = leaseId;
        this.leaseDuration = leaseDuration;
        this.renewable = renewable;
        this.data = data;
        this.warnings = warnings;
    }

    @JsonProperty("request_id")
    public String getRequestId() {
        return requestId;
    }

    @JsonProperty("request_id")
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @JsonProperty("lease_id")
    public String getLeaseId() {
        return leaseId;
    }

    @JsonProperty("lease_id")
    public void setLeaseId(String leaseId) {
        this.leaseId = leaseId;
    }

    @JsonProperty("lease_duration")
    public Integer getLeaseDuration() {
        return leaseDuration;
    }

    @JsonProperty("lease_duration")
    public void setLeaseDuration(Integer leaseDuration) {
        this.leaseDuration = leaseDuration;
    }

    @JsonProperty("renewable")
    public Boolean getRenewable() {
        return renewable;
    }

    @JsonProperty("renewable")
    public void setRenewable(Boolean renewable) {
        this.renewable = renewable;
    }

    @JsonProperty("data")
    public Data<T> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Data<T> data) {
        this.data = data;
    }

    @JsonProperty("warnings")
    public Object getWarnings() {
        return warnings;
    }

    @JsonProperty("warnings")
    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }
}
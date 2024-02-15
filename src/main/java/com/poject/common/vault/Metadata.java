
package com.poject.common.vault;

import com.fasterxml.jackson.annotation.*;
import jakarta.annotation.Generated;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "created_time",
    "custom_metadata",
    "deletion_time",
    "destroyed",
    "version"
})
@Generated("jsonschema2pojo")
public class Metadata implements Serializable
{

    @JsonProperty("created_time")
    private String createdTime;
    @JsonProperty("custom_metadata")
    private Object customMetadata;
    @JsonProperty("deletion_time")
    private String deletionTime;
    @JsonProperty("destroyed")
    private Boolean destroyed;
    @JsonProperty("version")
    private Integer version;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1102927501134452245L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Metadata() {
    }

    /**
     * 
     * @param destroyed
     * @param customMetadata
     * @param createdTime
     * @param deletionTime
     * @param version
     */
    public Metadata(String createdTime, Object customMetadata, String deletionTime, Boolean destroyed, Integer version) {
        super();
        this.createdTime = createdTime;
        this.customMetadata = customMetadata;
        this.deletionTime = deletionTime;
        this.destroyed = destroyed;
        this.version = version;
    }

    @JsonProperty("created_time")
    public String getCreatedTime() {
        return createdTime;
    }

    @JsonProperty("created_time")
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @JsonProperty("custom_metadata")
    public Object getCustomMetadata() {
        return customMetadata;
    }

    @JsonProperty("custom_metadata")
    public void setCustomMetadata(Object customMetadata) {
        this.customMetadata = customMetadata;
    }

    @JsonProperty("deletion_time")
    public String getDeletionTime() {
        return deletionTime;
    }

    @JsonProperty("deletion_time")
    public void setDeletionTime(String deletionTime) {
        this.deletionTime = deletionTime;
    }

    @JsonProperty("destroyed")
    public Boolean getDestroyed() {
        return destroyed;
    }

    @JsonProperty("destroyed")
    public void setDestroyed(Boolean destroyed) {
        this.destroyed = destroyed;
    }

    @JsonProperty("version")
    public Integer getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(Integer version) {
        this.version = version;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

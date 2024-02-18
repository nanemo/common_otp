package com.poject.common.model.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OTPRequest implements Serializable {
    private static final long serialVersionUID = -3924046329982906718L;
    private String login; // from property file
    private String key; // from property file
    private String receiver;
    private LocalDateTime scheduled;
    private Boolean unicode;
    private String text;
    private String sender;


}
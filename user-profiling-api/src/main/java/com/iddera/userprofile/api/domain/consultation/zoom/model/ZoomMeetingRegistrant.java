package com.iddera.userprofile.api.domain.consultation.zoom.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ZoomMeetingRegistrant implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String email;
    @JsonProperty("first_name")
    private String firstname;
    @JsonProperty("last_name")
    private String lastName;
    private String phone;
    @JsonProperty("auto_approve")
    private boolean autoApprove;
    @JsonProperty("join_url")
    private String joinUrl;
    @JsonProperty("registrant_id")
    private String registrantId;
    @JsonProperty("start_time")
    private LocalDateTime startTime;
    private String topic;
}

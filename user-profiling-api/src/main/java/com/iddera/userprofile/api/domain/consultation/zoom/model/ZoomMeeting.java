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
@Data
@Builder
public class ZoomMeeting implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String topic;
    private String status;
    private String uuid;
    private int type;
    @JsonProperty("start_time")
    private LocalDateTime startTime;
    private int duration;
    private String timezone;
    private String password;
    private String agenda;
    private ZoomSettings settings;
    @JsonProperty("created_at")
    private String createdAt;
}

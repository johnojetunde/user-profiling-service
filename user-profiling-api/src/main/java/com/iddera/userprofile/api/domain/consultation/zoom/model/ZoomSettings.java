package com.iddera.userprofile.api.domain.consultation.zoom.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ZoomSettings implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("host_video")
    private Boolean hostVideo;
    @JsonProperty("participant_video")
    private Boolean participantVideo;
    @JsonProperty("join_before_host")
    private Boolean joinBeforeHost;
    @JsonProperty("mute_upon_entry")
    private Boolean muteUponEntry;
    private Boolean watermark;
    @JsonProperty("approval_type")
    private Integer approvalType;
    @JsonProperty("registration_type")
    private Integer registrationType;
    private String audio;
    @JsonProperty("registrants_email_notification")
    private Boolean registrantsEmailNotification;
    @JsonProperty("contact_name")
    private String contactName;
    @JsonProperty("contact_email")
    private String contactEmail;
    @JsonProperty("registrants_confirmation_email")
    private Boolean registrantsConfirmationEmail;
    @JsonProperty("show_share_button")
    private Boolean showShareButton;
    @JsonProperty("allow_multiple_devices")
    private Boolean allowMultipleDevices;
}

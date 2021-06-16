package com.iddera.userprofile.api.domain.consultation.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MeetingRegistrant {
    private MeetingParticipant participantDetails;
    private String meetingId;
    private String registrantId;
    private String meetingUrl;
    private String meetingPasscode;
}

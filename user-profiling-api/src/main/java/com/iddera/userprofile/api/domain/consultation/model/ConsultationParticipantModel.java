package com.iddera.userprofile.api.domain.consultation.model;

import com.iddera.usermanagement.lib.domain.model.UserType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ConsultationParticipantModel {
    private Long id;
    private Long userId;
    private UserType userType;
    private String email;
    private String registrantId;
    private String meetingPasscode;
    private String meetingUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private boolean isDeleted;


}

package com.iddera.userprofile.api.domain.consultation.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ConsultationModel {
    private Long id;
    private DoctorTimeslotModel timeslot;
    private String meetingId;
    private ConsultationStatus status;
    private List<ConsultationParticipantModel> participants;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private boolean isDeleted;
}

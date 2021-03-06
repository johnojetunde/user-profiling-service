package com.iddera.userprofile.api.persistence.consultation.entity;

import com.iddera.usermanagement.lib.domain.model.UserType;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationParticipantModel;
import com.iddera.userprofile.api.domain.consultation.model.MeetingRegistrant;
import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "consultation_participant")
public class ConsultationParticipant extends BaseEntity {
    private String username;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private String email;
    private String registrantId;
    private String meetingPasscode;
    private String meetingUrl;
    @ManyToOne
    @JoinColumn(name = "consultation_id")
    @ToString.Exclude
    private Consultation consultation;

    public static ConsultationParticipant from(MeetingRegistrant reg, Consultation consultation) {
        return new ConsultationParticipant()
                .setConsultation(consultation)
                .setUsername(reg.getParticipantDetails().getUsername())
                .setEmail(reg.getParticipantDetails().getEmail())
                .setUserType(reg.getParticipantDetails().getUserType())
                .setRegistrantId(reg.getRegistrantId())
                .setMeetingPasscode(reg.getMeetingPasscode())
                .setMeetingUrl(reg.getMeetingUrl());
    }

    public ConsultationParticipantModel toModel() {
        return ConsultationParticipantModel.builder()
                .id(id)
                .username(username)
                .email(email)
                .userType(userType)
                .registrantId(registrantId)
                .meetingPasscode(meetingPasscode)
                .meetingUrl(meetingUrl)
                .createdAt(createdAt)
                .createdBy(createdBy)
                .updatedAt(updatedAt)
                .updatedBy(updatedBy)
                .isDeleted(isDeleted)
                .build();
    }
}

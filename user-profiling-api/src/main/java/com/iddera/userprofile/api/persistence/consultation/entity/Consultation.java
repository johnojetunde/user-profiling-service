package com.iddera.userprofile.api.persistence.consultation.entity;

import com.iddera.userprofile.api.domain.consultation.model.ConsultationMode;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationModel;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationStatus;
import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

import static com.iddera.usermanagement.lib.domain.utils.FunctionUtil.emptyIfNullStream;
import static java.util.stream.Collectors.toList;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "consultation")
public class Consultation extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "timeslot_id")
    private DoctorTimeslot timeslot;
    private String meetingId;
    @Enumerated(EnumType.STRING)
    private ConsultationStatus status;
    @Enumerated(EnumType.STRING)
    private ConsultationMode mode;
    @OneToMany(mappedBy = "consultation", fetch = EAGER, cascade = CascadeType.ALL)
    private List<ConsultationParticipant> participants;
    @OneToMany(mappedBy = "consultation", fetch = LAZY, cascade = CascadeType.ALL)
    private List<DrugPrescription> prescriptions;

    public ConsultationModel toModel() {
        var participantModels = emptyIfNullStream(participants)
                .map(ConsultationParticipant::toModel)
                .collect(toList());

        return ConsultationModel.builder()
                .id(id)
                .meetingId(meetingId)
                .status(status)
                .timeslot(timeslot.toModel())
                .participants(participantModels)
                .build();
    }
}

package com.iddera.userprofile.api.persistence.consultation.entity;

import com.iddera.userprofile.api.domain.consultation.model.DoctorNoteModel;
import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "doctor_note")
public class DoctorNote extends BaseEntity {
    private String history;
    private String examination;
    private String investigation;
    private String diagnosis;
    private String plan;

    @OneToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    public DoctorNoteModel toModel() {
        return DoctorNoteModel.builder()
                .history(history)
                .examination(examination)
                .investigation(investigation)
                .diagnosis(diagnosis)
                .plan(plan)
                .consultationId(consultation.getId())
                .id(id)
                .build();
    }
}

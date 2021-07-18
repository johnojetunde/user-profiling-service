package com.iddera.userprofile.api.persistence.consultation.entity;

import com.iddera.userprofile.api.domain.consultation.model.ConsultationNoteModel;
import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@Data
@Entity
@Table(name = "consultation_note")
public class ConsultationNote extends BaseEntity {
    private String history;
    private String examination;
    private String investigation;
    private String diagnosis;
    private String plan;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    public ConsultationNoteModel toModel() {
        return ConsultationNoteModel.builder()
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

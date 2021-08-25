package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalProcedureModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.RecoveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;

@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
@Table(name = "medical_procedure")
public class MedicalProcedure extends BaseMedicalEntity {
    private String name;
    private LocalDate dateAdmitted;
    @Enumerated(STRING)
    private RecoveryStatus recoveryStatus;
    private String comment;

    public MedicalProcedureModel toModel() {
        return MedicalProcedureModel.builder()
                .name(name)
                .dateAdmitted(dateAdmitted)
                .recoveryStatus(recoveryStatus)
                .comment(comment)
                .id(id)
                .username(username)
                .build();
    }
}

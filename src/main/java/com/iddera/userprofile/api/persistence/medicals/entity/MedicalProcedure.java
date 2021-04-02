package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalProcedureModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.RecoveryStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;

@Accessors(chain = true)
@Data
@Entity
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

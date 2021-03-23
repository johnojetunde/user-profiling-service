package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.CustomFrequency;
import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalProcedureModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.CustomFrequencyType;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "medical_procedure")
public class MedicalProcedure extends BaseMedicalEntity {
    private String name;
    private LocalDate date;
    private boolean hospitalized;
    private Integer recoveryPeriod;
    private CustomFrequencyType recoverPeriodType;
    private String comment;

    public MedicalProcedureModel toModel() {
        return MedicalProcedureModel.builder()
                .name(name)
                .date(date)
                .recoveryPeriod(new CustomFrequency(recoverPeriodType, recoveryPeriod))
                .comment(comment)
                .id(id)
                .username(username)
                .build();
    }
}

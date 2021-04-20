package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.CustomFrequency;
import com.iddera.userprofile.api.domain.medicalinfo.model.IllnessModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.CustomFrequencyType;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.RecoveryStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.LocalDate;

@Accessors(chain = true)
@Entity
@Table(name = "illness")
@Data
public class Illness extends BaseMedicalEntity {
    private String name;
    private Integer durationValue;
    @Enumerated(EnumType.STRING)
    private CustomFrequencyType durationType;
    private LocalDate dateAdmitted;
    @Enumerated(EnumType.STRING)
    private RecoveryStatus recoveryStatus;
    private String comment;

    public IllnessModel toModel() {
        return IllnessModel.builder()
                .name(name)
                .duration(new CustomFrequency(durationType, durationValue))
                .dateAdmitted(dateAdmitted)
                .recoveryStatus(recoveryStatus)
                .comment(comment)
                .id(id)
                .username(username)
                .build();
    }
}

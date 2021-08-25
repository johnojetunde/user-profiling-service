package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.CustomFrequency;
import com.iddera.userprofile.api.domain.medicalinfo.model.IllnessModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.CustomFrequencyType;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.RecoveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "illness")
@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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

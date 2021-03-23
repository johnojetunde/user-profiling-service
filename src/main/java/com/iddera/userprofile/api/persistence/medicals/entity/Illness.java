package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.IllnessModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.RecoveryStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.Duration;
import java.time.LocalDate;

@Accessors(chain = true)
@Entity
@Table(name = "illness")
@Data
public class Illness extends BaseMedicalEntity {
    private String name;
    private boolean ongoing;
    private Duration duration; //TODO: what does duration mean
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private RecoveryStatus recoveryStatus;
    private String comment;

    public IllnessModel toModel() {
        return IllnessModel.builder()
                .name(name)
                .ongoing(ongoing)
                .duration(duration)
                .recoveryStatus(recoveryStatus)
                .comment(comment)
                .id(id)
                .username(username)
                .build();
    }
}

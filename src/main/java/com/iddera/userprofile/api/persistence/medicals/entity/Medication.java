package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicationModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.ActiveStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.Duration;
import java.time.LocalDate;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "medication")
public class Medication extends BaseMedicalEntity {
    private String name;
    private String description;
    private String category;
    private Duration duration;
    private LocalDate startDate;
    private String prescriptionStatus;
    @Enumerated(EnumType.STRING)
    private ActiveStatus status;
    private String comment;

    public MedicationModel toModel() {
        return MedicationModel.builder()
                .name(name)
                .description(description)
                .category(category)
                .duration(duration)
                .startDate(startDate)
                .prescriptionStatus(prescriptionStatus)
                .status(status)
                .comment(comment)
                .id(id)
                .username(username)
                .build();
    }
}

package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.ExerciseInfoModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.FitnessRate;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "exercise_info")
public class ExerciseInfo extends BaseMedicalEntity {
    @Enumerated(EnumType.STRING)
    private FitnessRate rate;
    @ElementCollection
    private Set<String> activities;

    public ExerciseInfoModel toModel() {
        return ExerciseInfoModel.builder()
                .rate(rate)
                .activities(activities)
                .username(username)
                .id(id)
                .build();
    }
}

package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.SmokingHabitModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.Consumption;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.Frequency;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "smoking_habit")
public class SmokingHabit extends BaseMedicalEntity {
    @Enumerated(EnumType.STRING)
    private Frequency frequency;
    @Enumerated(EnumType.STRING)
    private Consumption consumption;

    public SmokingHabitModel toModel() {
        return SmokingHabitModel.builder()
                .frequency(frequency)
                .consumption(consumption)
                .id(id)
                .username(username)
                .build();
    }
}

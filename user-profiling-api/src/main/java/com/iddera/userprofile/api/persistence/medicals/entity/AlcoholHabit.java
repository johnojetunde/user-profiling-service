package com.iddera.userprofile.api.persistence.medicals.entity;

import com.iddera.userprofile.api.domain.medicalinfo.model.AlcoholHabitModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.Consumption;
import com.iddera.userprofile.api.domain.medicalinfo.model.enums.Frequency;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import static javax.persistence.EnumType.STRING;

@Accessors(chain = true)
@Data
@Entity
@Table(name = "alcohol_habit")
public class AlcoholHabit extends BaseMedicalEntity {
    @Enumerated(STRING)
    private Frequency frequency;
    @Enumerated(STRING)
    private Consumption consumption;

    public AlcoholHabitModel toModel() {
        return AlcoholHabitModel.builder()
                .id(id)
                .username(username)
                .consumption(consumption)
                .frequency(frequency)
                .build();

    }
}

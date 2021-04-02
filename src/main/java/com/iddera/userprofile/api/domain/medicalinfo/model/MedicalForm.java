package com.iddera.userprofile.api.domain.medicalinfo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MedicalForm {
    @Valid
    private SmokingHabitModel smokingInfo;
    @Valid
    private AlcoholHabitModel alcoholInfo;
    @NotNull
    @Valid
    private BloodDetailsModel bloodDetails;
    @Valid
    private List<DietaryPlanModel> dietaryPlans;
    @Valid
    private List<MedicationModel> medications;
    @Valid
    private List<IllnessModel> illnesses;
    @Valid
    private List<MedicalProcedureModel> medicalProcedures;
    @Valid
    private List<AllergyModel> allergies;
}

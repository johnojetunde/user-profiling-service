package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalAssessmentModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.abstracts.HospitalService;
import com.iddera.userprofile.api.persistence.EntityToDomainMapper;
import com.iddera.userprofile.api.persistence.medicals.entity.MedicalAssessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicalAssessmentMapper implements EntityToDomainMapper<MedicalAssessmentModel, MedicalAssessment> {
    private HospitalService hospitalService;

    @Autowired
    public MedicalAssessmentMapper(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    public MedicalAssessmentMapper() {
    }

    @Override
    public MedicalAssessment toEntity(MedicalAssessmentModel model) {
        return toEntity(model, model.getId());
    }

    @Override
    public MedicalAssessment toEntity(MedicalAssessmentModel model, Long id) {
        return MedicalAssessment.builder()
                .currentHealthFeel(model.getCurrentHealthFeel())
                .lastCheckUp(model.getLastCheckUp())
                .lastTimeFeltGood(model.getLastTimeFeltGood())
                .goodHealthMotivation(model.getGoodHealthMotivation())
                .averageBusyDay(model.getAverageBusyDay())
                .wellnessGoals(model.getWellnessGoals())
                .conditionsOrConcerns(model.getConditionsOrConcerns())
                .familyHeartDisease(model.getFamilyHeartDisease())
                .familyCancer(model.getFamilyCancer())
                .hadHeartOrCancerGenTest(model.getHadHeartOrCancerGenTest())
                .homePhysician(model.getHomePhysician())
                .haveFitnessPressureSensor(model.getHaveFitnessPressureSensor())
                .ableToGetPrescriptionDelivery(model.getAbleToGetPrescriptionDelivery())
                .takingStepsToImproveHealth(model.getTakingStepsToImproveHealth())
                .mentalStressTherapist(model.getMentalStressTherapist())
                .hospital(hospitalService.findById(model.getHospitalId()).join().toEntity())
                .id(id)
                .username(model.getUsername())
                .build();
    }

    @Override
    public MedicalAssessmentModel toModel(MedicalAssessment entity) {
        return entity.toModel();
    }
}

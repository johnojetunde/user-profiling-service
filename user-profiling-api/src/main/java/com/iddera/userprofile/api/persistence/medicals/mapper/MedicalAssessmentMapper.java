package com.iddera.userprofile.api.persistence.medicals.mapper;

import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalAssessmentModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.abstracts.HospitalService;
import com.iddera.userprofile.api.persistence.medicals.entity.MedicalAssessment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MedicalAssessmentMapper implements EntityToDomainMapper<MedicalAssessmentModel, MedicalAssessment> {
    private final HospitalService hospitalService;
    @Override
    public MedicalAssessment toEntity(MedicalAssessmentModel model) {
        return toEntity(model, model.getId());
    }

    @Override
    public MedicalAssessment toEntity(MedicalAssessmentModel model, Long id) {
        var entity = new MedicalAssessment().
                setCurrentHealthFeel(model.getCurrentHealthFeel())
                .setLastCheckUp(model.getLastCheckUp())
                .setLastTimeFeltGood(model.getLastTimeFeltGood())
                .setGoodHealthMotivation(model.getGoodHealthMotivation())
                .setAverageBusyDay(model.getAverageBusyDay())
                .setWellnessGoals(model.getWellnessGoals())
                .setConditionsOrConcerns(model.getConditionsOrConcerns())
                .setFamilyHeartDisease(model.getFamilyHeartDisease())
                .setFamilyCancer(model.getFamilyCancer())
                .setHadHeartOrCancerGenTest(model.getHadHeartOrCancerGenTest())
                .setHomePhysician(model.getHomePhysician())
                .setHaveFitnessPressureSensor(model.getHaveFitnessPressureSensor())
                .setAbleToGetPrescriptionDelivery(model.getAbleToGetPrescriptionDelivery())
                .setTakingStepsToImproveHealth(model.getTakingStepsToImproveHealth())
                .setMentalStressTherapist(model.getMentalStressTherapist())
                .setHospital(hospitalService.findById(model.getHospitalId()).join());


        entity.setId(id);
        entity.setUsername(model.getUsername());
        return entity;
    }

    @Override
    public MedicalAssessmentModel toModel(MedicalAssessment entity) {
        return entity.toModel();
    }
}

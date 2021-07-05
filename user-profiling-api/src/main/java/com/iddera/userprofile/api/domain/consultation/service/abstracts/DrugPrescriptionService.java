package com.iddera.userprofile.api.domain.consultation.service.abstracts;

import com.iddera.userprofile.api.domain.consultation.model.DrugPrescriptionModel;
import com.iddera.userprofile.api.domain.lab.model.LaboratoryModel;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DrugPrescriptionService {
    CompletableFuture<List<DrugPrescriptionModel>> findByConsultation(Long consultationId);

    CompletableFuture<DrugPrescriptionModel> findById(Long prescriptionId);

    CompletableFuture<DrugPrescriptionModel> update(DrugPrescriptionModel request);
}

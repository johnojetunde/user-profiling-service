package com.iddera.userprofile.api.domain.consultation.service.abstracts;

import com.iddera.userprofile.api.domain.consultation.model.DrugPrescriptionModel;
import com.iddera.userprofile.api.domain.user.model.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DrugPrescriptionService {
    CompletableFuture<List<DrugPrescriptionModel>> findByConsultation(Long consultationId,User user);

    CompletableFuture<DrugPrescriptionModel> findById(Long prescriptionId, User user);

    CompletableFuture<DrugPrescriptionModel> create(DrugPrescriptionModel request);
}

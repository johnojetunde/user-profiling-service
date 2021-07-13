package com.iddera.userprofile.api.domain.consultation.service.concretes;

import com.iddera.usermanagement.lib.domain.model.UserType;
import com.iddera.userprofile.api.domain.consultation.model.DrugPrescriptionModel;
import com.iddera.userprofile.api.domain.consultation.service.abstracts.DrugPrescriptionService;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import com.iddera.userprofile.api.persistence.consultation.entity.ConsultationParticipant;
import com.iddera.userprofile.api.persistence.consultation.entity.DrugPrescription;
import com.iddera.userprofile.api.persistence.consultation.persistence.ConsultationRepository;
import com.iddera.userprofile.api.persistence.consultation.persistence.DrugPrescriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.iddera.commons.utils.FunctionUtil.emptyIfNullStream;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultDrugPrescriptionService implements DrugPrescriptionService {
    private final DrugPrescriptionRepository drugPrescriptionRepository;
    private final ConsultationRepository consultationRepository;
    private final UserProfilingExceptionService exceptions;


    @Override
    public CompletableFuture<List<DrugPrescriptionModel>> findByConsultation(Long consultationId, User user) {
        return supplyAsync(() ->
                {
                    Consultation consultation = consultationRepository.findById(consultationId).orElseThrow(
                            () -> exceptions.handleCreateNotFoundException("Cannot find consultation with id %d", consultationId)
                    );
                    if (user.getUserType().equals(UserType.CLIENT))
                        ensureClientWasAParticipantInConsultation(consultation, user.getId());
                    return emptyIfNullStream(drugPrescriptionRepository.findByConsultation_Id(consultationId))
                            .map(DrugPrescription::toModel)
                            .collect(Collectors.toList());
                }
        );

    }

    @Override
    public CompletableFuture<DrugPrescriptionModel> findById(Long id, User user) {
        return supplyAsync(() -> {
            DrugPrescription drugPrescription = drugPrescriptionRepository.findById(id).orElseThrow(() ->
                    exceptions.handleCreateNotFoundException("Drug Prescription does not exist for %d", id));
           Consultation consultation = drugPrescription.getConsultation();
           if(user.getUserType().equals(UserType.CLIENT))
               ensureClientWasAParticipantInConsultation(consultation, user.getId());
           return drugPrescription.toModel();
        });
    }

    @Override
    public CompletableFuture<DrugPrescriptionModel> create(DrugPrescriptionModel request) {
        return supplyAsync(() -> {
            DrugPrescription prescription = new DrugPrescription();
            if (request.getId() != null) {
                prescription = drugPrescriptionRepository.findById(request.getId())
                        .orElseGet(DrugPrescription::new);
                ensureConsultationIdIsNotChanged(prescription.toModel(), request);
            }
            Consultation consultation = consultationRepository.findById(request.getConsultationId())
                    .orElseThrow(() ->
                            exceptions.handleCreateNotFoundException("Cannot find consultation with id %d", request.getConsultationId()));
            prescription.setName(request.getName())
                    .setDrugStrength(request.getDrugStrength())
                    .setDrugFormulation(request.getDrugFormulation())
                    .setConsultation(consultation)
                    .setQuantity(request.getQuantity())
                    .setName(request.getName())
                    .setUseInstructions(request.getUseInstructions())
                    .setNumberOfDays(request.getNumberOfDays());
            return drugPrescriptionRepository.save(prescription).toModel();
        });
    }

    private void ensureConsultationIdIsNotChanged(DrugPrescriptionModel originalModel, DrugPrescriptionModel updatedModel) {
        if (!originalModel.getConsultationId().equals(updatedModel.getConsultationId())) {
            throw exceptions.handleCreateBadRequest("Cannot change consultation Id of a drug prescription");
        }
    }

    private void ensureClientWasAParticipantInConsultation(Consultation consultation, Long userId) {
        List<ConsultationParticipant> participants = consultation.getParticipants().stream()
                .filter(consultationParticipant -> consultationParticipant.getUserId().equals(userId))
                .collect(Collectors.toList());
        if (participants.isEmpty())
            throw exceptions.handleCreateBadRequest("Client cannot have access to prescriptions whose consultation they aren't a participant of.");
    }
}

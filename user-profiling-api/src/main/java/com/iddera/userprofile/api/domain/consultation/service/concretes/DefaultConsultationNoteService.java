package com.iddera.userprofile.api.domain.consultation.service.concretes;

import com.iddera.userprofile.api.domain.consultation.model.ConsultationNoteModel;
import com.iddera.userprofile.api.domain.consultation.service.abstracts.ConsultationNoteService;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import com.iddera.userprofile.api.persistence.consultation.entity.ConsultationNote;
import com.iddera.userprofile.api.persistence.consultation.entity.ConsultationParticipant;
import com.iddera.userprofile.api.persistence.consultation.persistence.ConsultationNoteRepository;
import com.iddera.userprofile.api.persistence.consultation.persistence.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultConsultationNoteService implements ConsultationNoteService {
    private final ConsultationNoteRepository consultationNoteRepository;
    private final ConsultationRepository consultationRepository;
    private final UserProfilingExceptionService exceptions;

    @Override
    public CompletableFuture<ConsultationNoteModel> create(ConsultationNoteModel request, User user){
        return supplyAsync(() -> {
            Consultation consultation = consultationRepository.findById(request.getConsultationId())
                    .orElseThrow(()-> exceptions.handleCreateNotFoundException("Cannot find consultation with id %d.",request.getConsultationId()));
            ensureUserIsAConsultationParticipant(consultation,user);

            ConsultationNote consultationNote = new ConsultationNote();
            if (request.getId() != null) {
                consultationNote = consultationNoteRepository.findById(request.getId())
                        .orElseGet(ConsultationNote::new);
                ensureConsultationIdIsNotChanged(consultationNote.toModel(),request);
            }
            consultationNote.setInvestigation(request.getInvestigation())
                    .setExamination(request.getExamination())
                    .setDiagnosis(request.getDiagnosis())
                    .setPlan(request.getPlan())
                    .setHistory(request.getHistory())
                    .setConsultation(consultation);
            return consultationNoteRepository.save(consultationNote).toModel();
        });
    }

    public void ensureUserIsAConsultationParticipant(Consultation consultation, User user){
        List<Long> userIds = consultation.getParticipants().stream()
                             .map(ConsultationParticipant::getUserId)
                             .collect(Collectors.toList());
        if(!userIds.contains(user.getId())){
            throw  exceptions.handleCreateBadRequest("User is not a participant of this consultation.");
        }
    }

    @Override
    public CompletableFuture<ConsultationNoteModel> findById(Long id){
        return supplyAsync(() -> consultationNoteRepository.findById(id).orElseThrow(() ->
                exceptions.handleCreateNotFoundException("Doctor's note with id :%d does not exist.", id)).toModel());
    }

    @Override
    public CompletableFuture<ConsultationNoteModel> findByConsultation(Long id){
        return supplyAsync(() -> consultationNoteRepository.findByConsultation_Id(id).orElseThrow(() ->
                exceptions.handleCreateNotFoundException("Doctor's note does not exist for consultation with id: %d.", id)).toModel());
    }

    private void ensureConsultationIdIsNotChanged(ConsultationNoteModel originalModel, ConsultationNoteModel updatedModel){
        if(!originalModel.getConsultationId().equals(updatedModel.getConsultationId())){
            throw exceptions.handleCreateBadRequest("Cannot change consultation Id of a consultation note.");
        }
    }
}

package com.iddera.userprofile.api.domain.consultation.service.concretes;

import com.iddera.userprofile.api.domain.consultation.model.DoctorNoteModel;
import com.iddera.userprofile.api.domain.consultation.service.abstracts.DoctorNoteService;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import com.iddera.userprofile.api.persistence.consultation.entity.DoctorNote;
import com.iddera.userprofile.api.persistence.consultation.persistence.ConsultationRepository;
import com.iddera.userprofile.api.persistence.consultation.persistence.DoctorNoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RequiredArgsConstructor
@Service
public class DoctorNoteImplService implements DoctorNoteService {
    private final DoctorNoteRepository doctorNoteRepository;
    private final ConsultationRepository consultationRepository;
    private final UserProfilingExceptionService exceptions;

    @Override
    public CompletableFuture<DoctorNoteModel> create(DoctorNoteModel request){
        return supplyAsync(() -> {
            DoctorNote doctorNote = new DoctorNote();
            if (request.getId() != null) {
                doctorNote = doctorNoteRepository.findById(request.getId())
                        .orElseGet(DoctorNote::new);
                ensureConsultationIdIsNotChanged(doctorNote.toModel(),request);
            }
            Consultation consultation = consultationRepository.findById(request.getConsultationId())
                    .orElseThrow(()-> exceptions.handleCreateNotFoundException("Cannot find consultation with id %d.",request.getConsultationId()));
            doctorNote.setInvestigation(request.getInvestigation())
                    .setExamination(request.getExamination())
                    .setDiagnosis(request.getDiagnosis())
                    .setPlan(request.getPlan())
                    .setHistory(request.getHistory())
                    .setConsultation(consultation);
            return doctorNoteRepository.save(doctorNote).toModel();
        });
    }

    @Override
    public CompletableFuture<DoctorNoteModel> findById(Long id){
        return supplyAsync(() -> doctorNoteRepository.findById(id).orElseThrow(() ->
                exceptions.handleCreateNotFoundException("Doctor's note with id :%d does not exist.", id)).toModel());
    }

    @Override
    public CompletableFuture<DoctorNoteModel> findByConsultation(Long id){
        return supplyAsync(() -> doctorNoteRepository.findByConsultation_Id(id).orElseThrow(() ->
                exceptions.handleCreateNotFoundException("Doctor's note does not exist for consultation with id: %d.", id)).toModel());
    }

    private void ensureConsultationIdIsNotChanged(DoctorNoteModel originalModel,DoctorNoteModel updatedModel){
        if(!originalModel.getConsultationId().equals(updatedModel.getConsultationId())){
            throw exceptions.handleCreateBadRequest("Cannot change consultation Id of a drug prescription.");
        }
    }
}

package com.iddera.userprofile.api.domain.consultation.service.abstracts;


import com.iddera.userprofile.api.domain.consultation.model.DoctorNoteModel;

import java.util.concurrent.CompletableFuture;

public interface DoctorNoteService {
    CompletableFuture<DoctorNoteModel> create(DoctorNoteModel request);
    CompletableFuture<DoctorNoteModel> findById(Long noteId);
    CompletableFuture<DoctorNoteModel> findByConsultation(Long consultationId);
}

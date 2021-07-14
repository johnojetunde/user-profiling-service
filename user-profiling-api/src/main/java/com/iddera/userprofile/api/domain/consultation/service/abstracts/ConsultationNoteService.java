package com.iddera.userprofile.api.domain.consultation.service.abstracts;


import com.iddera.userprofile.api.domain.consultation.model.ConsultationNoteModel;
import com.iddera.userprofile.api.domain.model.User;

import java.util.concurrent.CompletableFuture;

public interface ConsultationNoteService {
    CompletableFuture<ConsultationNoteModel> create(ConsultationNoteModel request, User user);
    CompletableFuture<ConsultationNoteModel> findById(Long noteId);
    CompletableFuture<ConsultationNoteModel> findByConsultation(Long consultationId);
}

package com.iddera.userprofile.api.domain.consultation.service.abstracts;


import com.iddera.userprofile.api.domain.consultation.model.ConsultationNoteModel;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationNoteUpdateModel;
import com.iddera.userprofile.api.domain.user.model.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ConsultationNoteService {
    CompletableFuture<ConsultationNoteModel> create(ConsultationNoteModel request, User user);
    CompletableFuture<ConsultationNoteModel> update(Long id, ConsultationNoteUpdateModel request, User user);
    CompletableFuture<ConsultationNoteModel> findById(Long noteId);
    CompletableFuture<List<ConsultationNoteModel>> findNotesByConsultation(Long consultationId);
}

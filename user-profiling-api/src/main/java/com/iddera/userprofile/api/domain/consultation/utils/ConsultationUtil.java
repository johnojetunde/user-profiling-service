package com.iddera.userprofile.api.domain.consultation.utils;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.user.model.User;
import com.iddera.userprofile.api.persistence.consultation.entity.Consultation;
import com.iddera.userprofile.api.persistence.consultation.entity.ConsultationParticipant;

import static com.iddera.commons.utils.FunctionUtil.emptyIfNullStream;

public class ConsultationUtil {
    private ConsultationUtil() {
    }

    public static void ensureUserIsAConsultationParticipant(Consultation consultation, User user, UserProfilingExceptionService exceptions) {
        boolean isUserAParticipant = emptyIfNullStream(consultation.getParticipants())
                .map(ConsultationParticipant::getUsername)
                .anyMatch(uName -> user.getUsername().equals(uName));
        if (!isUserAParticipant) {
            throw exceptions.handleCreateForbidden("User is not a participant of this consultation");
        }
    }
}

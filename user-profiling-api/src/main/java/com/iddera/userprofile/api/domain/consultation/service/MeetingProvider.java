package com.iddera.userprofile.api.domain.consultation.service;

import com.iddera.userprofile.api.domain.consultation.model.MeetingProviderKey;
import com.iddera.userprofile.api.domain.consultation.model.RegisteredParticipant;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface MeetingProvider {

    MeetingProviderKey key();

    CompletableFuture<List<RegisteredParticipant>> scheduleMeet(MeetingDetails meetingDetails);
}

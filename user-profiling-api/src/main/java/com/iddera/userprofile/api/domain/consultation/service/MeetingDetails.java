package com.iddera.userprofile.api.domain.consultation.service;

import com.iddera.userprofile.api.domain.consultation.model.MeetingParticipant;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@Getter
public class MeetingDetails {
    List<MeetingParticipant> participants;
    LocalDate date;
    LocalTime time;
    Integer duration;
    String title;
    String agenda;
}

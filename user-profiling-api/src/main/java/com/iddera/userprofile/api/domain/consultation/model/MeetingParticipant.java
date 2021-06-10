package com.iddera.userprofile.api.domain.consultation.model;

import com.iddera.usermanagement.lib.domain.model.UserType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MeetingParticipant {
    private final int userId;
    private final String email;
    private final String firstname;
    private final String lastname;
    private final UserType userType;
}

package com.iddera.userprofile.api.domain.consultation.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

import static com.iddera.usermanagement.lib.domain.utils.FunctionUtil.emptyIfNull;

@Accessors(chain = true)
@Data
public class ConsultationSearchCriteria {
    private Long timeslotId;
    private List<Long> participantUserIds;

    public ConsultationSearchCriteria addUserId(Long userId) {
        participantUserIds = new ArrayList<>(emptyIfNull(participantUserIds));
        participantUserIds.add(userId);
        return this;
    }
}

package com.iddera.userprofile.api.domain.consultation.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

import static com.iddera.commons.utils.FunctionUtil.emptyIfNull;

@Accessors(chain = true)
@Data
public class ConsultationSearchCriteria {
    private Long timeslotId;
    private List<String> participantUsernames;

    public ConsultationSearchCriteria addUsername(String username) {
        participantUsernames = new ArrayList<>(emptyIfNull(participantUsernames));
        participantUsernames.add(username);
        return this;
    }
}

package com.iddera.userprofile.api.domain.consultation.model;

import com.iddera.commons.annotation.ValidEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@Data
public class ConsultationRequest {
    @NotNull(message = "Timeslot id is required")
    private Long timeslotId;
    private String agenda;
    @ValidEnum(message = "Invalid Consultation mode")
    private ConsultationMode mode;
}

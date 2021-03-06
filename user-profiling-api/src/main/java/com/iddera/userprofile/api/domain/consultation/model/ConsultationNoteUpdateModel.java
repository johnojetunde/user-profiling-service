package com.iddera.userprofile.api.domain.consultation.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsultationNoteUpdateModel {
    private String history;
    private String examination;
    private String investigation;
    private String diagnosis;
    private String plan;
}

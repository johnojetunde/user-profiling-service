package com.iddera.userprofile.api.domain.consultation.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ConsultationNoteModel {
    private Long id;
    @NotBlank(message = "History can not be null or empty.")
    private String history;
    @NotBlank(message = "Examination can not be null or empty.")
    private String examination;
    @NotBlank(message = "Investigation can not be null or empty.")
    private String investigation;
    @NotBlank(message = "Diagnosis can not be null or empty.")
    private String diagnosis;
    @NotBlank(message = "Plan can not be null or empty.")
    private String plan;
    @NotNull(message = "Consultation Id can not be null")
    private Long consultationId;
}

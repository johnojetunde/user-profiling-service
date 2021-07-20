package com.iddera.userprofile.api.domain.consultation.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ConsultationNoteModel {
    private Long id;
    @NotBlank(message = "History is required.")
    private String history;
    @NotBlank(message = "Examination is required.")
    private String examination;
    @NotBlank(message = "Investigation is required.")
    private String investigation;
    @NotBlank(message = "Diagnosis is required.")
    private String diagnosis;
    @NotBlank(message = "Plan is required.")
    private String plan;
    @NotNull(message = "Consultation Id is required.")
    private Long consultationId;
}

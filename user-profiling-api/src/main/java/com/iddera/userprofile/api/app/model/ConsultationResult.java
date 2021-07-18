package com.iddera.userprofile.api.app.model;

import com.iddera.userprofile.api.domain.consultation.model.ConsultationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class ConsultationResult extends PageImpl<ConsultationModel> {
    public ConsultationResult(Page<ConsultationModel> pagedContent) {
        super(pagedContent.getContent(), pagedContent.getPageable(), pagedContent.getTotalElements());
    }
}

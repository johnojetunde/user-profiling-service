package com.iddera.userprofile.api.app.model;

import com.iddera.userprofile.api.domain.consultation.model.DoctorTimeslotModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class TimeslotResult extends PageImpl<DoctorTimeslotModel> {
    public TimeslotResult(Page<DoctorTimeslotModel> pagedContent) {
        super(pagedContent.getContent(), pagedContent.getPageable(), pagedContent.getTotalElements());
    }
}

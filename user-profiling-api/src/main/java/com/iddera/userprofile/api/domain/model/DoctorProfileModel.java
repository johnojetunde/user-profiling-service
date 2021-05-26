package com.iddera.userprofile.api.domain.model;

import com.iddera.userprofile.api.persistence.doctorprofile.entity.DoctorProfile;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class DoctorProfileModel {

    private Long id;

    private Long userId;
    private String designation;
    private String educationInfo;
    private String bio;
    private String interest;


    public DoctorProfile toEntity() {
        var entity = new DoctorProfile()
                .setUserId(userId)
                .setBio(bio)
                .setDesignation(designation)
                .setInterest(interest)
                .setEducationInfo(educationInfo);
        entity.setId(id);
        return entity;
    }
}

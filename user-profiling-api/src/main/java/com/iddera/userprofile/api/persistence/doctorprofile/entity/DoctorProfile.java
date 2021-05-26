package com.iddera.userprofile.api.persistence.doctorprofile.entity;

import com.iddera.userprofile.api.domain.model.DoctorProfileModel;
import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Accessors(chain = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "doctor_profile")
public class DoctorProfile extends BaseEntity {
    @Column(unique = true, nullable = false)
    private Long userId;
    private String designation;
    private String educationInfo;
    private String bio;
    private String interest;


    public DoctorProfileModel toModel() {
        return new DoctorProfileModel()
                .setUserId(userId)
                .setInterest(interest)
                .setBio(bio)
                .setDesignation(designation)
                .setId(id)
                .setEducationInfo(educationInfo);
    }
}

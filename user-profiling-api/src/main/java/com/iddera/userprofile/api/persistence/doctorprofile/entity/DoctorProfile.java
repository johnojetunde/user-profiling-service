package com.iddera.userprofile.api.persistence.doctorprofile.entity;

import com.iddera.userprofile.api.domain.doctor.model.DoctorProfileModel;
import com.iddera.userprofile.api.persistence.userprofile.entity.BaseEntity;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.util.Objects;

@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@Accessors(chain = true)
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

    public DoctorProfile() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        DoctorProfile that = (DoctorProfile) o;
        return userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId);
    }

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

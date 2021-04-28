package com.iddera.userprofile.api.persistence.userprofile.entity;

import com.iddera.usermanagement.lib.domain.model.Gender;
import com.iddera.userprofile.api.domain.user.enums.MaritalStatus;
import com.iddera.userprofile.api.domain.user.model.UserProfileModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDate;

import static java.util.Optional.ofNullable;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_profile")
public class UserProfile extends BaseEntity {
    @Column(unique = true, nullable = false)
    private Long userId;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    private LocalDate dateOfBirth;
    @ManyToOne
    @JoinColumn(name = "lga_id", referencedColumnName = "id", nullable = false)
    private LocalGovernmentArea lga;

    public UserProfileModel toModel() {
        return new UserProfileModel()
                .setUserId(userId)
                .setGender(gender)
                .setMaritalStatus(maritalStatus)
                .setDateOfBirth(dateOfBirth)
                .setLga(ofNullable(lga)
                        .map(LocalGovernmentArea::toModel)
                        .orElse(null));
    }
}
package com.iddera.userprofile.api.persistence.userprofile.entity;

import com.iddera.usermanagement.lib.domain.model.Gender;
import com.iddera.userprofile.api.domain.user.enums.MaritalStatus;
import com.iddera.userprofile.api.domain.user.model.UserProfileModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

import static java.util.Optional.ofNullable;

@EntityListeners(AuditingEntityListener.class)
@Accessors(chain = true)
@NoArgsConstructor
@Data
@Entity
@SuperBuilder
@Table(name = "user_profile")
public class UserProfile extends BaseEntity {
    private String username;
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
                .setId(id)
                .setUsername(username)
                .setGender(gender)
                .setMaritalStatus(maritalStatus)
                .setDateOfBirth(dateOfBirth)
                .setLga(ofNullable(lga)
                        .map(LocalGovernmentArea::toModel)
                        .orElse(null));
    }

    public static UserProfile fromModel(UserProfileModel model) {
        return UserProfile.builder()
                .id(model.getId())
                .username(model.getUsername())
                .gender(model.getGender())
                .maritalStatus(model.getMaritalStatus())
                .dateOfBirth(model.getDateOfBirth())
                .lga(ofNullable(model.getLga())
                        .map(LocalGovernmentArea::fromModel)
                        .orElse(null))
                .build();
    }
}

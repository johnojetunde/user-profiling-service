package com.iddera.userprofile.api.persistence.userprofile.entity;

import com.iddera.userprofile.api.domain.userprofile.enums.Gender;
import com.iddera.userprofile.api.domain.userprofile.enums.MaritalStatus;
import com.iddera.userprofile.api.domain.userprofile.model.UserProfileModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

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

     @ManyToOne
     @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
     private Country country;

     @ManyToOne
     @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false)
     private State state;

     @ManyToOne
     @JoinColumn(name = "lga_id", referencedColumnName = "id", nullable = false)
     private LocalGovernmentArea lga;

     public UserProfileModel toModel() {
          return new UserProfileModel()
                     .setUserId(getUserId())
                     .setGender(getGender())
                     .setMaritalStatus(getMaritalStatus())
                     .setCountryId(getCountry().getId())
                     .setStateId(getState().getId())
                     .setLgaId(getLga().getId());
     }
}

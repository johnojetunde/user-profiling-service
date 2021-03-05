package com.iddera.entity;

import com.iddera.enums.Gender;
import com.iddera.enums.MaritalStatus;
import com.iddera.model.UserProfileModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
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

     @ManyToOne(targetEntity = Country.class)
     @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
     private Country country;

     @ManyToOne(targetEntity = State.class)
     @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false)
     private State state;

     @ManyToOne(targetEntity = LocalGovernmentArea.class)
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

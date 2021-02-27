package com.iddera.entity;

import com.iddera.enums.Gender;
import com.iddera.enums.MaritalStatus;
import com.iddera.model.BaseEntity;
import com.iddera.model.Location;
import com.iddera.model.UserModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
@Table(name = "user_profiles")
public class UserProfile extends BaseEntity {
     @Column(unique = true, nullable = false)
     @NotNull(message = "User Id can not be null")
     private Long user_id;

     @NotNull(message = "Gender can not be null")
     private Gender gender;

     @NotNull(message = "Marital status can not be null")
     private MaritalStatus maritalStatus;

     @Embedded
     @Valid
     @NotNull(message = "Location can not be null")
     private Location location;
}

package com.iddera.userprofile.api.persistence.userprofile.entity;

import com.iddera.userprofile.api.domain.location.model.StateModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static java.util.Optional.ofNullable;

@EntityListeners(AuditingEntityListener.class)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "state")
public class State extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    @ManyToOne(targetEntity = Country.class)
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private Country country;

    public StateModel toModel() {
        return StateModel.builder()
                .id(getId())
                .name(getName())
                .code(getCode())
                .country(ofNullable(getCountry())
                        .map(Country::toModel)
                        .orElse(null))
                .build();
    }

    public static State fromEntity(StateModel stateModel) {
        return State.builder()
                .id(stateModel.getId())
                .name(stateModel.getName())
                .code(stateModel.getCode())
                .country(ofNullable(stateModel.getCountry())
                        .map(Country::fromModel)
                        .orElse(null))
                .build();
    }
}
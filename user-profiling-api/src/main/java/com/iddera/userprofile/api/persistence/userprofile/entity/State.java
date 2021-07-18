package com.iddera.userprofile.api.persistence.userprofile.entity;

import com.iddera.userprofile.api.domain.location.model.StateModel;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static java.util.Optional.ofNullable;

@EntityListeners(AuditingEntityListener.class)
@Data
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
}
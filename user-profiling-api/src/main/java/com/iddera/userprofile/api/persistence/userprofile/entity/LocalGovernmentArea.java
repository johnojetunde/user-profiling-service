package com.iddera.userprofile.api.persistence.userprofile.entity;

import com.iddera.userprofile.api.domain.location.model.LocalGovernmentAreaModel;
import lombok.Data;

import javax.persistence.*;

import static java.util.Optional.ofNullable;

@Data
@Entity
@Table(name = "local_government_area")
public class LocalGovernmentArea extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne(targetEntity = State.class)
    @JoinColumn(name = "state_id", referencedColumnName = "id", nullable = false)
    private State state;

    public LocalGovernmentAreaModel toModel() {
        return LocalGovernmentAreaModel.builder()
                .id(getId())
                .name(getName())
                .code(getCode())
                .state(ofNullable(getState())
                        .map(State::toModel)
                        .orElse(null))
                .build();
    }
}
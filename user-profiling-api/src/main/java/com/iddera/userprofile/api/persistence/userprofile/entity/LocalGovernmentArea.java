package com.iddera.userprofile.api.persistence.userprofile.entity;

import com.iddera.userprofile.api.domain.location.model.LocalGovernmentAreaModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import static java.util.Optional.ofNullable;

@EntityListeners(AuditingEntityListener.class)
@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
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

    public static LocalGovernmentArea fromModel(LocalGovernmentAreaModel model) {
        return LocalGovernmentArea.builder()
                .id(model.getId())
                .name(model.getName())
                .code(model.getCode())
                .state(ofNullable(model.getState())
                        .map(State::fromEntity)
                        .orElse(null))
                .build();
    }
}
package com.iddera.entity;

import lombok.Data;

import javax.persistence.*;

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
}
package com.iddera.userprofile.api.domain.medicalinfo.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

import static com.iddera.commons.utils.FunctionUtil.isNullOrEmpty;

public enum BloodGroup {
    @JsonProperty("A+")
    A_POSITIVE("A+"),
    @JsonProperty("A-")
    A_NEGATIVE("A-"),
    @JsonProperty("B+")
    B_POSITIVE("B+"),
    @JsonProperty("B-")
    B_NEGATIVE("B-"),
    @JsonProperty("O+")
    O_POSITIVE("O+"),
    @JsonProperty("O-")
    O_NEGATIVE("O-"),
    @JsonProperty("AB+")
    AB_POSITIVE("AB+"),
    @JsonProperty("AB-")
    AB_NEGAT("AB-"),
    @JsonProperty("unknown")
    UNKNOWN("UNKNOWN");

    private final String bloodGroup;

    BloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    @Override
    public String toString() {
        return this.bloodGroup;
    }

    @JsonValue
    public String getName() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static BloodGroup create(String value) {
        if (isNullOrEmpty(value)) {
            return null;
        }

        return Stream.of(BloodGroup.values())
                .filter(e -> e.toString().equalsIgnoreCase(value)).findFirst()
                .orElse(UNKNOWN);
    }
}

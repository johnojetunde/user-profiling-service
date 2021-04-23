package com.iddera.userprofile.api.domain.medicalinfo.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

import static com.iddera.commons.utils.FunctionUtil.isNullOrEmpty;

public enum Consumption {
    HIGH,
    MODERATE,
    LITTLE,
    UNKNOWN;

    @JsonValue
    public String getName() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static Consumption create(String value) {
        if (isNullOrEmpty(value)) {
            return null;
        }

        return Stream.of(Consumption.values())
                .filter(e -> e.toString().equalsIgnoreCase(value)).findFirst()
                .orElse(UNKNOWN);
    }
}

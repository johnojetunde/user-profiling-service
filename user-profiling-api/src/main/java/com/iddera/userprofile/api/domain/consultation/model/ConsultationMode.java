package com.iddera.userprofile.api.domain.consultation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

import static com.iddera.commons.utils.FunctionUtil.isNullOrEmpty;

public enum ConsultationMode {
    AUDIO,
    VIDEO,
    PHONE,
    UNKNOWN;

    @JsonValue
    public String getName() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static ConsultationMode create(String value) {
        if (isNullOrEmpty(value)) {
            return null;
        }

        return Stream.of(ConsultationMode.values())
                .filter(e -> e.toString().equalsIgnoreCase(value)).findFirst()
                .orElse(UNKNOWN);
    }
}

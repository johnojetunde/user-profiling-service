package com.iddera.userprofile.api.domain.medicalinfo.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

import static com.iddera.commons.utils.FunctionUtil.isNullOrEmpty;

public enum RecoveryStatus {
    FULLY,
    PARTLY,
    NOT_RECOVERED,
    UNKNOWN;

    @JsonValue
    public String getName() {
        return name().toLowerCase();
    }

    @JsonCreator
    public static RecoveryStatus create(String value) {
        if (isNullOrEmpty(value)) {
            return null;
        }

        return Stream.of(RecoveryStatus.values())
                .filter(e -> e.toString().equalsIgnoreCase(value)).findFirst()
                .orElse(UNKNOWN);
    }
}

package com.iddera.userprofile.api.domain.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

import static com.iddera.commons.utils.FunctionUtil.isNullOrEmpty;

public enum MaritalStatus {
    SINGLE,
    MARRIED,
    DIVORCED,
    UNKNOWN;

    @JsonCreator
    public static MaritalStatus create(String value) {
        if (isNullOrEmpty(value)) {
            return null;
        }

        return Stream.of(MaritalStatus.values())
                .filter(e -> e.toString().equalsIgnoreCase(value)).findFirst()
                .orElse(UNKNOWN);
    }
}

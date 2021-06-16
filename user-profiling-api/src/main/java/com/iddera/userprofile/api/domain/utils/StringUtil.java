package com.iddera.userprofile.api.domain.utils;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class StringUtil {
    private StringUtil() {
    }

    public static String generatePasscode() {
        return randomAlphanumeric(5, 9);
    }
}

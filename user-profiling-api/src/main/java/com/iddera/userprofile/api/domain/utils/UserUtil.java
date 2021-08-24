package com.iddera.userprofile.api.domain.utils;

import lombok.SneakyThrows;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

public final class UserUtil {
    private static final Long MIN_NUMBER = 40L;
    private static final Long MAX_NUMBER = 100L;

    private UserUtil() {
        throw new IllegalStateException("Utility method");
    }

    @SneakyThrows
    public static String generateUsername(String email, String firstname) {
        var messageDigest = MessageDigest.getInstance("MD5");

        messageDigest.update(email.getBytes());
        byte[] digest = messageDigest.digest();

        String emailHash = DatatypeConverter.printHexBinary(digest).toLowerCase();

        return firstname.toLowerCase() +
                emailHash.substring(0, 3) +
                randomDigit();
    }

    private static Long randomDigit() {
        return Math.round(Math.random() * (MAX_NUMBER - MIN_NUMBER + 1) + MIN_NUMBER);
    }
}

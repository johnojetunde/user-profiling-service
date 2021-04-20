package com.iddera.userprofile.api.domain.exception;

import com.iddera.commons.exception.ApiException;

import java.util.Map;

public class UserProfilingException extends ApiException {
    public UserProfilingException() {
    }

    public UserProfilingException(String message) {
        super(message);
    }

    public UserProfilingException(int code, String message, Throwable throwable) {
        super(message, throwable, code, Map.of());
    }
}

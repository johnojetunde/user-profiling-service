package com.iddera.exception;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class UserProfilingException extends ApiException {
    public UserProfilingException() {
    }

    public UserProfilingException(Throwable throwable) {
        super(throwable);
    }

    public UserProfilingException(String message) {
        super(message);
    }

    public UserProfilingException(String message, Object... args) {
        super(format(message, args));
    }

    public UserProfilingException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public UserProfilingException(String message, Throwable throwable, int code, Map<String, List<String>> responseHeaders, String responseBody) {
        super(message, throwable, code, responseHeaders, responseBody);
    }

    public UserProfilingException(String message, int code, Map<String, List<String>> responseHeaders, String responseBody) {
        super(message, code, responseHeaders, responseBody);
    }

    public UserProfilingException(String message, Throwable throwable, int code, Map<String, List<String>> responseHeaders) {
        super(message, throwable, code, responseHeaders);
    }

    public UserProfilingException(int code, Map<String, List<String>> responseHeaders, String responseBody) {
        super(code, responseHeaders, responseBody);
    }

    public UserProfilingException(int code, String message) {
        super(code, message);
    }

    public UserProfilingException(int code, String message, Map<String, List<String>> responseHeaders, String responseBody) {
        super(code, message, responseHeaders, responseBody);
    }
}

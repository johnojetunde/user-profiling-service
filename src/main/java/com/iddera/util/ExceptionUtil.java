package com.iddera.util;

import com.iddera.exception.ApiException;
import com.iddera.exception.UserProfilingException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.function.Function;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_IMPLEMENTED;

@UtilityClass
@Slf4j
public class ExceptionUtil {
    public static <T> CompletableFuture<T> notImplementedFeature(String userTpe) {
        return completeExceptionally(createNotImplementedException(
                format("This feature is yet to be supported for the %s", userTpe)));
    }

    public static <T> CompletableFuture<T> completeExceptionally(Throwable ex) {
        CompletableFuture<T> failedFuture = new CompletableFuture<>();
        failedFuture.completeExceptionally(ex);
        return failedFuture;
    }

    public static CompletableFuture<Boolean> completeBooleanExceptionally(String message) {
        return completeExceptionally(new UserProfilingException(message));
    }

    public static <T> Function<Throwable, T> reportException(String msg) {
        return rootEx -> handleRethrowException(msg, rootEx);
    }

    public static RuntimeException handleCreateException(String msg, Throwable rootEx) {
        Throwable ex = unpackCompletionException(rootEx);
        if (ex instanceof UserProfilingException) {
            return new CompletionException(ex);
        }
        return userMgtException(msg, ex);
    }

    public static RuntimeException createNotImplementedException(String msg) {
        return new UserProfilingException(NOT_IMPLEMENTED.value(), msg);
    }

    public static RuntimeException handleCreateException(String msg, Object... args) {
        return userMgtException(format(msg, args), INTERNAL_SERVER_ERROR.value());
    }

    public static RuntimeException handleCreateException(String msg, HttpStatus code, Object... args) {
        return userMgtException(format(msg, args), code.value());
    }

    public static RuntimeException handleCreateBadRequest(String msg, Object... args) {
        return handleCreateException(msg, HttpStatus.BAD_REQUEST, args);
    }

    public static RuntimeException handleCreateNotFoundException(String msg, Object... args) {
        return handleCreateException(msg, HttpStatus.NOT_FOUND, args);
    }

    public static RuntimeException handleCreateUnAuthorized(String msg) {
        return handleCreateException(msg, HttpStatus.UNAUTHORIZED);
    }

    public static RuntimeException handleCreateForbidden(String msg) {
        return handleCreateException(msg, HttpStatus.FORBIDDEN);
    }

    public static <T> T handleRethrowException(String msg, Throwable rootEx) {
        throw handleCreateException(msg, rootEx);
    }

    private static UserProfilingException userMgtException(String msg, Throwable ex) {
        logException(msg, ex);
        return new UserProfilingException(msg, ex, findExceptionCode(ex), null);
    }

    private static UserProfilingException userMgtException(String msg, int code) {
        log.error(msg);
        return new UserProfilingException(code, msg);
    }

    private static Throwable unpackCompletionException(Throwable e) {
        if (e instanceof CompletionException && e.getCause() != null) {
            return e.getCause();
        }
        return e;
    }

    private static void logException(String msg, Throwable ex) {
        Optional<ApiException> apiException = findApiException(ex);
        if (apiException.isPresent()) {
            ApiException apiEx = apiException.get();
            log.error(msg + ",\n error code: {}\nresponseHeaders: {}\nresponseBody: {}\n",
                    apiEx.getCode(),
                    apiEx.getResponseHeaders(),
                    apiEx.getResponseBody(),
                    ex);
        } else {
            log.error(msg, ex);
        }
    }

    private static int findExceptionCode(Throwable ex) {
        return findApiException(ex)
                .map(ApiException::getCode)
                .orElse(INTERNAL_SERVER_ERROR.value());
    }

    private static Optional<ApiException> findApiException(Throwable ex) {
        return ((List<?>) ExceptionUtils.getThrowableList(ex)).stream()
                .filter(ApiException.class::isInstance)
                .map(ApiException.class::cast)
                .findFirst();
    }

    private static String appendExceptionMessage(String msg, Throwable ex) {
        String exMsg = getExceptionMessage(ex);

        if (msg == null || msg.trim().isEmpty()) {
            return exMsg;
        }

        return msg + ": " + exMsg;
    }

    private static String getExceptionMessage(Throwable ex) {
        if (ex instanceof ApiException) {
            ApiException apiEx = (ApiException) ex;
            return String.format("ApiException %s/%s", apiEx.getCode(), compactWhitespace(apiEx.getMessage()));
        } else {
            return ex.getMessage();
        }
    }

    private static String compactWhitespace(String s) {
        if (s == null) {
            return null;
        }
        return s.replaceAll("\\s+", " ").trim();
    }
}

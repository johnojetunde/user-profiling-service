package com.iddera.userprofile.api.domain.exception;

import com.iddera.commons.utils.ExceptionUtil;
import org.springframework.stereotype.Service;

@Service
public class UserProfilingExceptionService extends ExceptionUtil<UserProfilingException> {

    @Override
    public UserProfilingException createCustomException(String s, Throwable throwable, int i) {
        return new UserProfilingException(i, s, throwable);
    }

    @Override
    public Class<UserProfilingException> customExceptionClass() {
        return UserProfilingException.class;
    }
}

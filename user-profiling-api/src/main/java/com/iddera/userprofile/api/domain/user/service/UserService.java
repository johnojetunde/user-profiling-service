package com.iddera.userprofile.api.domain.user.service;

import com.iddera.client.model.Page;
import com.iddera.client.util.ErrorHandler;
import com.iddera.usermanagement.client.endpoints.Users;
import com.iddera.usermanagement.lib.app.request.*;
import com.iddera.usermanagement.lib.domain.model.LoginModel;
import com.iddera.usermanagement.lib.domain.model.OauthToken;
import com.iddera.usermanagement.lib.domain.model.UserModel;
import com.iddera.usermanagement.lib.domain.model.UserType;
import com.iddera.userprofile.api.app.config.ClientProperties;
import com.iddera.userprofile.api.app.model.LoginResponse;
import com.iddera.userprofile.api.app.model.NewUserModel;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final Users users;
    private final ClientProperties clientProperties;
    private final UserProfilingExceptionService exceptions;

    public CompletableFuture<UserModel> createUser(NewUserModel request, UserType userType) {
        var userRequest = toUserRequest(request, userType);
        return users.create(userRequest)
                .thenApply(ErrorHandler::handleExceptionally);
    }

    public CompletableFuture<Page<UserModel>> getAll(Long pageNumber,
                                                     Long pageSize,
                                                     UserType userType,
                                                     String token) {
        return users.getAll(pageNumber, pageSize, userType, token)
                .thenApply(ErrorHandler::handleExceptionally);
    }

    public CompletableFuture<UserModel> getById(Long userId, String token) {
        return users.getById(userId, token)
                .thenApply(ErrorHandler::handleExceptionally);
    }

    public CompletableFuture<UserModel> changePassword(Long userId, ChangeUserPasswordRequest request) {
        return users.changePassword(userId, request)
                .thenApply(ErrorHandler::handleExceptionally);
    }

    public CompletableFuture<UserModel> verifyEmail(UserVerificationRequest request) {
        return users.verifyEmail(request)
                .thenApply(ErrorHandler::handleExceptionally);
    }

    public CompletableFuture<UserModel> resetPasswordInitiate(EmailModel request) {
        return users.initiateResetPassword(request)
                .thenApply(ErrorHandler::handleExceptionally);
    }

    public CompletableFuture<UserModel> resetPassword(ForgotPasswordRequest request) {
        return users.resetPassword(request)
                .thenApply(ErrorHandler::handleExceptionally);
    }

    public CompletableFuture<LoginResponse> login(LoginModel loginModel) {
        return users.login(clientProperties.getClientId(), clientProperties.getClientSecret(), loginModel)
                .thenApply(this::toLoginResponse
                ).exceptionally(e -> {
                    log.error("Error logging in user", e);
                    throw exceptions.handleCreateUnAuthorized("Invalid credentials");
                });
    }

    public CompletableFuture<LoginResponse> refreshToken(String refreshToken) {
        return users.refreshToken(clientProperties.getClientId(), clientProperties.getClientSecret(), refreshToken)
                .thenApply(this::toLoginResponse
                ).exceptionally(e -> {
                    log.error("Error logging in user", e);
                    throw exceptions.handleCreateUnAuthorized("Invalid credentials");
                });
    }

    private LoginResponse toLoginResponse(OauthToken re) {
        return new LoginResponse()
                .setAccessToken(re.getAccessToken())
                .setRefreshToken(re.getRefreshToken())
                .setExpiresIn(re.getExpiresIn())
                .setScope(re.getScope())
                .setJti(re.getJti())
                .setTokenType(re.getTokenType());
    }

    private UserRequest toUserRequest(NewUserModel request, UserType userType) {
        return new UserRequest()
                .setFirstName(request.getFirstName())
                .setLastName(request.getLastName())
                .setEmail(request.getEmail())
                .setPassword(request.getPassword())
                .setConfirmPassword(request.getConfirmPassword())
                .setType(userType)
                .setUsername(request.getEmail());
    }
}

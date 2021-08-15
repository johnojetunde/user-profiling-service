package com.iddera.userprofile.api.app.controller;

import com.iddera.usermanagement.lib.app.request.EmailModel;
import com.iddera.usermanagement.lib.app.request.ForgotPasswordRequest;
import com.iddera.usermanagement.lib.app.request.UserVerificationRequest;
import com.iddera.usermanagement.lib.app.response.EmailValidationResponse;
import com.iddera.usermanagement.lib.domain.model.LoginModel;
import com.iddera.usermanagement.lib.domain.model.UserModel;
import com.iddera.userprofile.api.app.model.LoginResponse;
import com.iddera.userprofile.api.app.model.RefreshToken;
import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.user.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/login")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = LoginResponse.class)})
    public CompletableFuture<ResponseModel> login(@Valid @RequestBody LoginModel loginModel) {
        return userService.login(loginModel)
                .thenApply(ResponseModel::new);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/refresh-token")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = LoginResponse.class)})
    public CompletableFuture<ResponseModel> refreshToken(@Valid @RequestBody RefreshToken refreshToken) {
        return userService.refreshToken(refreshToken.getRefreshToken())
                .thenApply(ResponseModel::new);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/verify-email")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = String.class)})
    public CompletableFuture<ResponseModel> verifyEmail(@Valid @RequestBody UserVerificationRequest request) {
        return userService.verifyEmail(request)
                .thenApply(__ -> "Email verified successfully")
                .thenApply(ResponseModel::new);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/validate-email")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = EmailValidationResponse.class)})
    public CompletableFuture<ResponseModel> validateEmail(@Valid @RequestBody EmailModel request) {
        return userService.validateEmail(request)
                .thenApply(ResponseModel::new);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/reset-password/initiate")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = String.class)})
    public CompletableFuture<ResponseModel> resetPasswordInitiate(@Valid @RequestBody EmailModel request) {
        return userService.resetPasswordInitiate(request)
                .thenApply(__ -> "Password reset link have been sent to your email")
                .thenApply(ResponseModel::new);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/reset-password/update")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserModel.class)})
    public CompletableFuture<ResponseModel> resetPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return userService.resetPassword(request)
                .thenApply(ResponseModel::new);
    }
}

package com.iddera.userprofile.api.app.controller;

import com.iddera.usermanagement.lib.app.request.ChangeUserPasswordRequest;
import com.iddera.usermanagement.lib.domain.model.UserModel;
import com.iddera.userprofile.api.app.model.NewUserModel;
import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.domain.user.service.UserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

import static com.iddera.usermanagement.lib.domain.model.UserType.CLIENT;
import static com.iddera.usermanagement.lib.domain.model.UserType.DOCTOR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/users", produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/clients")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserModel.class)})
    public CompletableFuture<ResponseModel> createClient(@Valid @RequestBody NewUserModel request) {
        return userService.createUser(request, CLIENT)
                .thenApply(ResponseModel::new);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/doctors")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserModel.class)})
    public CompletableFuture<ResponseModel> createDoctor(@Valid @RequestBody NewUserModel request) {
        return userService.createUser(request, DOCTOR)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/change-password")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserModel.class)})
    public CompletableFuture<ResponseModel> changePassword(@AuthenticationPrincipal User user,
                                                           @Valid @RequestBody ChangeUserPasswordRequest request) {
        return userService.changePassword(user.getId(), request)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping(value = "/current")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserModel.class)})
    public CompletableFuture<ResponseModel> get(@AuthenticationPrincipal UserDetails authentication) {
        User user = (User) authentication;
        return userService.getById(user.getId(), authentication.getPassword())
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','ADMIN')")
    @GetMapping(value = "/{userId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserModel.class)})
    public CompletableFuture<ResponseModel> getById(@PathVariable("userId") Long userId,
                                                    @AuthenticationPrincipal UserDetails authentication) {
        return userService.getById(userId, authentication.getPassword())
                .thenApply(ResponseModel::new);
    }
}

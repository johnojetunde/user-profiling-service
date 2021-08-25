package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.app.model.UserProfileUpdateRequest;
import com.iddera.userprofile.api.domain.user.model.User;
import com.iddera.userprofile.api.domain.user.model.UserProfileModel;
import com.iddera.userprofile.api.domain.user.service.UserProfileService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/clients", produces = APPLICATION_JSON_VALUE)
public class ClientController {

    private final UserProfileService userProfileService;

    @PreAuthorize("hasAuthority('CLIENT')")
    @PutMapping(consumes = APPLICATION_JSON_VALUE, value = "/{username}/profile")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserProfileModel.class)})
    public CompletableFuture<ResponseModel> updateProfile(@PathVariable("username") String username,
                                                          @Valid @RequestBody UserProfileUpdateRequest request,
                                                          @AuthenticationPrincipal User user) {
        return userProfileService.update(username, user, request)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping(value = "/current/profile")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserProfileModel.class)})
    public CompletableFuture<ResponseModel> getProfile(@AuthenticationPrincipal User user) {
        return userProfileService.get(user)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','ADMIN')")
    @GetMapping(value = "/{username}/profile")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserProfileModel.class)})
    public CompletableFuture<ResponseModel> getProfileByUsername(@PathVariable("username") String username) {
        return userProfileService.get(username)
                .thenApply(ResponseModel::new);
    }
}

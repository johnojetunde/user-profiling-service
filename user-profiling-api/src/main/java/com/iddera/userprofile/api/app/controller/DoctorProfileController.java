package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.DoctorProfileUpdateRequest;
import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.doctor.DoctorProfileService;
import com.iddera.userprofile.api.domain.model.DoctorProfileModel;
import com.iddera.userprofile.api.domain.model.User;
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
@RequestMapping(value = "/v1/doctor", produces = APPLICATION_JSON_VALUE)
public class DoctorProfileController {

    private final DoctorProfileService userProfileService;

    @PreAuthorize("hasAuthority('CLIENT')")
    @PutMapping(consumes = APPLICATION_JSON_VALUE, value = "/{userId}/profile")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DoctorProfileModel.class)})
    public CompletableFuture<ResponseModel> createProfile(@PathVariable("userId") Long userId,
                                                          @Valid @RequestBody DoctorProfileUpdateRequest request,
                                                          @AuthenticationPrincipal User user) {
        return userProfileService.update(userId, user, request)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAuthority('CLIENT')")
    @GetMapping(value = "/current/profile")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DoctorProfileModel.class)})
    public CompletableFuture<ResponseModel> getProfile(@AuthenticationPrincipal User user) {
        return userProfileService.get(user)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','ADMIN')")
    @GetMapping(value = "/{userId}/profile")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DoctorProfileModel.class)})
    public CompletableFuture<ResponseModel> getProfileById(@PathVariable("userId") Long userId) {
        return userProfileService.get(userId)
                .thenApply(ResponseModel::new);
    }
}

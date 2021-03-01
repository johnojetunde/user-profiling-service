package com.iddera.controller;

import com.iddera.model.ResponseModel;
import com.iddera.model.UserProfileModel;
import com.iddera.model.request.UserProfileRequest;
import com.iddera.service.UserProfileService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user-profile", produces = APPLICATION_JSON_VALUE)
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserProfileModel.class)})
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody UserProfileRequest request) {
        return userProfileService.create(request)
                .thenApply(ResponseModel::new);
    }

    @PutMapping("/{userId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserProfileModel.class)})
    public CompletableFuture<ResponseModel> update(@Valid @RequestBody UserProfileRequest request,
                                                   @PathVariable long userId) {
        return userProfileService.update(userId, request)
                .thenApply(ResponseModel::new);
    }
}

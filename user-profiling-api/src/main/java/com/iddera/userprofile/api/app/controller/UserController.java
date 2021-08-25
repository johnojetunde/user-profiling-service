package com.iddera.userprofile.api.app.controller;

import com.iddera.usermanagement.lib.app.request.ChangeUserPasswordRequest;
import com.iddera.usermanagement.lib.domain.model.UserModel;
import com.iddera.userprofile.api.app.model.NewClientModel;
import com.iddera.userprofile.api.app.model.NewUserModel;
import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.user.model.User;
import com.iddera.userprofile.api.domain.user.service.UserService;
import com.iddera.userprofile.api.domain.user.service.UserSignupService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    private final UserSignupService userSignupService;
    private final UserService userService;


    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/clients")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserModel.class)})
    public CompletableFuture<ResponseModel> createClient(@Valid @RequestBody NewClientModel request) {
        return userSignupService.createClient(request)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('CLIENT','DOCTOR','ADMIN')")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/change-password")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserModel.class)})
    public CompletableFuture<ResponseModel> changePassword(@AuthenticationPrincipal User user,
                                                           @Valid @RequestBody ChangeUserPasswordRequest request) {
        return userService.changePassword(user.getId(), request)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('CLIENT','DOCTOR','ADMIN')")
    @GetMapping(value = "/current")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserModel.class)})
    public CompletableFuture<ResponseModel> get(@AuthenticationPrincipal UserDetails authentication) {
        User user = (User) authentication;
        return userService.getById(user.getId(), authentication.getPassword())
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','ADMIN')")
    @GetMapping(value = "/{username}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserModel.class)})
    public CompletableFuture<ResponseModel> getByUsername(@PathVariable("username") String username,
                                                          @AuthenticationPrincipal UserDetails authentication) {
        return userService.getByUsername(username, authentication.getPassword())
                .thenApply(ResponseModel::new);
    }

    /**
     * TODO: to be reviewed as soon as we get the flow for the doctor onboarding
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/doctors")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserModel.class)})
    public CompletableFuture<ResponseModel> createDoctor(@Valid @RequestBody NewUserModel request) {
        return userService.createUser(request, DOCTOR)
                .thenApply(ResponseModel::new);
    }

    @GetMapping(value = "/doctors")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserModel.class)})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page number", example = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "page size", example = "10", dataType = "int", paramType = "query")
    })
    public CompletableFuture<ResponseModel> getAllDoctors(@RequestParam(value = "page", required = false) Long pageNumber,
                                                          @RequestParam(value = "size", required = false) Long pageSize,
                                                          @AuthenticationPrincipal UserDetails authentication) {
        return userService.getAll(pageNumber, pageSize, DOCTOR, authentication.getPassword())
                .thenApply(ResponseModel::new);
    }

    @GetMapping(value = "/clients")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = UserModel.class)})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "page number", example = "0", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "page size", example = "10", dataType = "int", paramType = "query")
    })
    public CompletableFuture<ResponseModel> getAllClients(@RequestParam(value = "page", required = false) Long pageNumber,
                                                          @RequestParam(value = "size", required = false) Long pageSize,
                                                          @AuthenticationPrincipal UserDetails authentication) {
        return userService.getAll(pageNumber, pageSize, CLIENT, authentication.getPassword())
                .thenApply(ResponseModel::new);
    }
}

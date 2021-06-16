package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationModel;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationRequest;
import com.iddera.userprofile.api.domain.consultation.service.ConsultationService;
import com.iddera.userprofile.api.domain.model.User;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/consulations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/clients/schedule")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ConsultationModel.class)})
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody ConsultationRequest request,
                                                   @AuthenticationPrincipal User user) {
        return consultationService.book(request, user)
                .thenApply(ResponseModel::new);
    }
}

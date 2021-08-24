package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ConsultationResult;
import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationModel;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationRequest;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationSearchCriteria;
import com.iddera.userprofile.api.domain.consultation.service.ConsultationService;
import com.iddera.userprofile.api.domain.model.User;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

import static com.iddera.usermanagement.lib.domain.model.UserType.ADMIN;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/consultations")
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


    @PreAuthorize("hasAnyAuthority('DOCTOR','CLIENT')")
    @GetMapping(consumes = APPLICATION_JSON_VALUE, value = "/{id}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ConsultationModel.class)})
    public CompletableFuture<ResponseModel> getById(@PathVariable("id") Long id,
                                                    @AuthenticationPrincipal User user) {
        return consultationService.getById(id, user)
                .thenApply(ResponseModel::new);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/searches")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ConsultationResult.class)})
    public CompletableFuture<ResponseModel> searchConsultation(@Valid @RequestBody ConsultationSearchCriteria request,
                                                               @PageableDefault(sort = "id", direction = ASC) Pageable pageable,
                                                               @AuthenticationPrincipal User user) {
        if (!ADMIN.equals(user.getUserType())) {
            request.addUsername(user.getUsername());
        }
        return consultationService.search(request, pageable)
                .thenApply(ConsultationResult::new)
                .thenApply(ResponseModel::new);
    }
}

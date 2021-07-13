package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.consultation.model.DrugPrescriptionModel;
import com.iddera.userprofile.api.domain.consultation.service.abstracts.DrugPrescriptionService;
import com.iddera.userprofile.api.domain.model.User;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/prescriptions")
@RequiredArgsConstructor
public class DrugPrescriptionController {
    private final DrugPrescriptionService drugPrescriptionService;

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DrugPrescriptionModel.class)})
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody DrugPrescriptionModel prescriptionModel) {
        return drugPrescriptionService.create(prescriptionModel)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','CLIENT')")
    @GetMapping("/consultation/{id}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DrugPrescriptionModel.class)})
    public CompletableFuture<ResponseModel> getPrescriptionsByConsultation(@PathVariable("id") Long consultationId,@AuthenticationPrincipal User user) {
        return drugPrescriptionService.findByConsultation(consultationId,user)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','CLIENT')")
    @GetMapping("/{id}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DrugPrescriptionModel.class)})
    public CompletableFuture<ResponseModel> getPrescription(@PathVariable("id") Long prescriptionId,@AuthenticationPrincipal User user) {
        return drugPrescriptionService.findById(prescriptionId,user)
                .thenApply(ResponseModel::new);
    }
}

package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.consultation.model.DrugPrescriptionModel;
import com.iddera.userprofile.api.domain.consultation.service.abstracts.DrugPrescriptionService;
import com.iddera.userprofile.api.domain.lab.model.LaboratoryModel;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/prescriptions")
@RequiredArgsConstructor
public class DrugPrescriptionController {
    private final DrugPrescriptionService drugPrescriptionService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DrugPrescriptionModel.class)})
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody DrugPrescriptionModel prescriptionModel) {
        return drugPrescriptionService.update(prescriptionModel)
                .thenApply(ResponseModel::new);
    }

    @GetMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DrugPrescriptionModel.class)})
    public CompletableFuture<ResponseModel> getPrescriptionsByConsultation(Long consultationId) {
        return drugPrescriptionService.findByConsultation(consultationId)
                .thenApply(ResponseModel::new);
    }

    @GetMapping("/{prescription}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DrugPrescriptionModel.class)})
    public CompletableFuture<ResponseModel> getPrescriptions(@PathVariable Long prescriptionId) {
        return drugPrescriptionService.findById(prescriptionId)
                .thenApply(ResponseModel::new);
    }
}

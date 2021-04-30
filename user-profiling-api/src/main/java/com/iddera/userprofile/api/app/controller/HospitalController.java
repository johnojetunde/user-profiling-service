package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.abstracts.HospitalService;
import com.iddera.userprofile.api.domain.model.HospitalModel;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/hospital")
@RequiredArgsConstructor
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = HospitalModel.class)})
    public CompletableFuture<ResponseModel> getHospitals() {
        return hospitalService.findAll()
                .thenApply(ResponseModel::new);
    }

    @GetMapping("/{hospitalId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = HospitalModel.class)})
    public CompletableFuture<ResponseModel> getHospital(@PathVariable Long hospitalId) {
        return hospitalService.findById(hospitalId)
                .thenApply(ResponseModel::new);
    }

    @PostMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = HospitalModel.class)})
    public CompletableFuture<ResponseModel> createHospitals(@Validated @RequestBody HospitalModel hospitalModel) {
        return hospitalService.update(hospitalModel)
                .thenApply(ResponseModel::new);
    }
}

package com.iddera.userprofile.api.app.controller.medicalinfo;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.MedicationModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.concretes.MedicalInfoService;
import com.iddera.userprofile.api.domain.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/medications")
public class MedicationController extends AbstractMedicalInfoController<MedicationModel> {
    public MedicationController(MedicalInfoService<MedicationModel> service) {
        super(service);
    }

    @ApiOperation(value = "Create a medication", response = MedicationModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicationModel.class)})
    @Override
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody MedicationModel body,
                                                   @AuthenticationPrincipal User user) {
        return super.create(body, user);
    }

    @ApiOperation(value = "Update a medication", response = MedicationModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicationModel.class)})
    @Override
    public CompletableFuture<ResponseModel> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody MedicationModel body,
                                                   @AuthenticationPrincipal User user) {
        return super.update(id, body, user);
    }

    @ApiOperation(value = "get a medication by id", response = MedicationModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicationModel.class)})
    @Override
    public CompletableFuture<ResponseModel> getById(@PathVariable("id") Long id,
                                                    @AuthenticationPrincipal User user) {
        return super.getById(id, user);
    }

    @ApiOperation(value = "get all user's medications", response = MedicationModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicationModel.class, responseContainer = "List")})
    @Override
    public CompletableFuture<ResponseModel> getAll(@AuthenticationPrincipal User user) {
        return super.getAll(user);
    }
}

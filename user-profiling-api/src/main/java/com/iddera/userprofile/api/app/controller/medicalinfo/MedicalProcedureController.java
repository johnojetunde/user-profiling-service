package com.iddera.userprofile.api.app.controller.medicalinfo;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalProcedureModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.concretes.MedicalInfoService;
import com.iddera.userprofile.api.domain.user.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/medical-procedures")
public class MedicalProcedureController extends AbstractMedicalInfoController<MedicalProcedureModel> {
    public MedicalProcedureController(MedicalInfoService<MedicalProcedureModel> service) {
        super(service);
    }

    @PostMapping
    @ApiOperation(value = "Create a medical-procedure", response = MedicalProcedureModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalProcedureModel.class)})
    @Override
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody MedicalProcedureModel body,
                                                   @AuthenticationPrincipal User user) {
        return super.create(body, user);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a medical-procedure", response = MedicalProcedureModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalProcedureModel.class)})
    @Override
    public CompletableFuture<ResponseModel> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody MedicalProcedureModel body,
                                                   @AuthenticationPrincipal User user) {
        return super.update(id, body, user);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get a medical-procedure by id", response = MedicalProcedureModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalProcedureModel.class)})
    @Override
    public CompletableFuture<ResponseModel> getById(@PathVariable("id") Long id,
                                                    @AuthenticationPrincipal User user) {
        return super.getById(id, user);
    }

    @GetMapping
    @ApiOperation(value = "get all user's medical-procedures", response = MedicalProcedureModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalProcedureModel.class, responseContainer = "List")})
    @Override
    public CompletableFuture<ResponseModel> getAll(@AuthenticationPrincipal User user) {
        return super.getAll(user);
    }
}

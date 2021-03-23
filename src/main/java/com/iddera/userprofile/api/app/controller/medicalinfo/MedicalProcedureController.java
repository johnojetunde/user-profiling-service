package com.iddera.userprofile.api.app.controller.medicalinfo;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalProcedureModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.MedicalInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/medical-procedures")
public class MedicalProcedureController extends AbstractMedicalInfoController<MedicalProcedureModel> {
    public MedicalProcedureController(MedicalInfoService<MedicalProcedureModel> service) {
        super(service);
    }

    @ApiOperation(value = "Create a medical-procedure", response = MedicalProcedureModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalProcedureModel.class)})
    @Override
    public CompletableFuture<ResponseModel> create(@Valid MedicalProcedureModel body) {
        return super.create(body);
    }

    @ApiOperation(value = "Update a medical-procedure", response = MedicalProcedureModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalProcedureModel.class)})
    @Override
    public CompletableFuture<ResponseModel> update(Long id, @Valid MedicalProcedureModel body) {
        return super.update(id, body);
    }

    @ApiOperation(value = "get a medical-procedure by id", response = MedicalProcedureModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalProcedureModel.class)})
    @Override
    public CompletableFuture<ResponseModel> getById(Long id) {
        return super.getById(id);
    }

    @ApiOperation(value = "get all user's medical-procedures", response = MedicalProcedureModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalProcedureModel.class, responseContainer = "List")})
    @Override
    public CompletableFuture<ResponseModel> getAll() {
        return super.getAll();
    }
}

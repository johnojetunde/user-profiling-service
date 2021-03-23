package com.iddera.userprofile.api.app.controller.medicalinfo;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.ExerciseInfoModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.MedicalInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/exercise-infos")
public class ExerciseInfoController extends AbstractMedicalInfoController<ExerciseInfoModel> {
    public ExerciseInfoController(MedicalInfoService<ExerciseInfoModel> service) {
        super(service);
    }

    @ApiOperation(value = "Create an exercise-info", response = ExerciseInfoModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ExerciseInfoModel.class)})
    @Override
    public CompletableFuture<ResponseModel> create(@Valid ExerciseInfoModel body) {
        return super.create(body);
    }

    @ApiOperation(value = "Update an exercise-info", response = ExerciseInfoModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ExerciseInfoModel.class)})
    @Override
    public CompletableFuture<ResponseModel> update(Long id, @Valid ExerciseInfoModel body) {
        return super.update(id, body);
    }

    @ApiOperation(value = "get an exercise-info by id", response = ExerciseInfoModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ExerciseInfoModel.class)})
    @Override
    public CompletableFuture<ResponseModel> getById(Long id) {
        return super.getById(id);
    }

    @ApiOperation(value = "get all user's exercise-infos", response = ExerciseInfoModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ExerciseInfoModel.class, responseContainer = "List")})
    @Override
    public CompletableFuture<ResponseModel> getAll() {
        return super.getAll();
    }
}

package com.iddera.userprofile.api.app.controller.medicalinfo;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.SmokingHabitModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.MedicalInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/smoking-habits")
public class SmokingHabitController extends AbstractMedicalInfoController<SmokingHabitModel> {
    public SmokingHabitController(MedicalInfoService<SmokingHabitModel> service) {
        super(service);
    }

    @ApiOperation(value = "Create a smoking-habit", response = SmokingHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = SmokingHabitModel.class)})
    @Override
    public CompletableFuture<ResponseModel> create(@Valid SmokingHabitModel body) {
        return super.create(body);
    }

    @ApiOperation(value = "Update a smoking-habit", response = SmokingHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = SmokingHabitModel.class)})
    @Override
    public CompletableFuture<ResponseModel> update(Long id, @Valid SmokingHabitModel body) {
        return super.update(id, body);
    }

    @ApiOperation(value = "get a smoking-habit by id", response = SmokingHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = SmokingHabitModel.class)})
    @Override
    public CompletableFuture<ResponseModel> getById(Long id) {
        return super.getById(id);
    }

    @ApiOperation(value = "get all user's smoking-habits", response = SmokingHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = SmokingHabitModel.class, responseContainer = "List")})
    @Override
    public CompletableFuture<ResponseModel> getAll() {
        return super.getAll();
    }
}

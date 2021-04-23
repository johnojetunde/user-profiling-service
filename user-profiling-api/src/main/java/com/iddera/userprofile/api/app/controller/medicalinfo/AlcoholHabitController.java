package com.iddera.userprofile.api.app.controller.medicalinfo;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.AlcoholHabitModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.MedicalInfoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/alcohol-habits")
public class AlcoholHabitController extends AbstractMedicalInfoController<AlcoholHabitModel> {
    public AlcoholHabitController(MedicalInfoService<AlcoholHabitModel> service) {
        super(service);
    }

    @ApiOperation(value = "Create an alcohol-habit", response = AlcoholHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AlcoholHabitModel.class)})
    @Override
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody AlcoholHabitModel body) {
        return super.create(body);
    }

    @ApiOperation(value = "Update an alcohol-habit", response = AlcoholHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AlcoholHabitModel.class)})
    @Override
    public CompletableFuture<ResponseModel> update(@PathVariable("id") Long id, @Valid @RequestBody AlcoholHabitModel body) {
        return super.update(id, body);
    }

    @ApiOperation(value = "get an alcohol-habit by id", response = AlcoholHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AlcoholHabitModel.class)})
    @Override
    public CompletableFuture<ResponseModel> getById(@PathVariable("id") Long id) {
        return super.getById(id);
    }

    @ApiOperation(value = "get all user's alcohol-habits", response = AlcoholHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AlcoholHabitModel.class, responseContainer = "List")})
    @Override
    public CompletableFuture<ResponseModel> getAll() {
        return super.getAll();
    }
}

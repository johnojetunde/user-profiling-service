package com.iddera.userprofile.api.app.controller.medicalinfo;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalAssessmentModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.SmokingHabitModel;
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
@RequestMapping("/medical-assessment")
public class MedicalAssessmentController extends AbstractMedicalInfoController<MedicalAssessmentModel> {
    public MedicalAssessmentController(MedicalInfoService<MedicalAssessmentModel> service) {
        super(service);
    }

    @ApiOperation(value = "Create a medical-assessment", response = SmokingHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = SmokingHabitModel.class)})
    @Override
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody MedicalAssessmentModel body) {
        return super.create(body);
    }

    @ApiOperation(value = "Update a smoking-habit", response = SmokingHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = SmokingHabitModel.class)})
    @Override
    public CompletableFuture<ResponseModel> update(@PathVariable("id") Long id, @Valid @RequestBody MedicalAssessmentModel body) {
        return super.update(id, body);
    }

    @ApiOperation(value = "get a smoking-habit by id", response = MedicalAssessmentModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalAssessmentModel.class)})
    @Override
    public CompletableFuture<ResponseModel> getById(@PathVariable("id") Long id) {
        return super.getById(id);
    }

    @ApiOperation(value = "get all user's smoking-habits", response = MedicalAssessmentModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalAssessmentModel.class, responseContainer = "List")})
    @Override
    public CompletableFuture<ResponseModel> getAll() {
        return super.getAll();
    }
}

package com.iddera.userprofile.api.app.controller.medicalinfo;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalAssessmentModel;
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
@RequestMapping("/medical-assessment")
public class MedicalAssessmentController extends AbstractMedicalInfoController<MedicalAssessmentModel> {
    public MedicalAssessmentController(MedicalInfoService<MedicalAssessmentModel> service) {
        super(service);
    }

    @ApiOperation(value = "Create a medical-assessment", response = MedicalAssessmentModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalAssessmentModel.class)})
    @Override
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody MedicalAssessmentModel body,@AuthenticationPrincipal User user) {
        return super.create(body,user);
    }

    @ApiOperation(value = "Update a medical-assessment", response = MedicalAssessmentModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalAssessmentModel.class)})
    @Override
    public CompletableFuture<ResponseModel> update(@PathVariable("id") Long id, @Valid @RequestBody MedicalAssessmentModel body,@AuthenticationPrincipal User user) {
        return super.update(id, body,user);
    }

    @ApiOperation(value = "get a medical-assessment by id", response = MedicalAssessmentModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalAssessmentModel.class)})
    @Override
    public CompletableFuture<ResponseModel> getById(@PathVariable("id") Long id,@AuthenticationPrincipal User user) {
        return super.getById(id,user);
    }

    @ApiOperation(value = "get all user's medical-assessment", response = MedicalAssessmentModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalAssessmentModel.class, responseContainer = "List")})
    @Override
    public CompletableFuture<ResponseModel> getAll(@AuthenticationPrincipal User user) {
        return super.getAll(user);
    }
}

package com.iddera.userprofile.api.app.controller.medicalinfo;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.AllergyModel;
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
@RequestMapping("/allergies")
public class AllergyController extends AbstractMedicalInfoController<AllergyModel> {
    public AllergyController(MedicalInfoService<AllergyModel> service) {
        super(service);
    }

    @ApiOperation(value = "Create an allergy", response = AllergyModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AllergyModel.class)})
    @Override
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody AllergyModel body) {
        return super.create(body);
    }

    @ApiOperation(value = "Update an allergy", response = AllergyModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AllergyModel.class)})
    @Override
    public CompletableFuture<ResponseModel> update(@PathVariable("id") Long id, @Valid @RequestBody AllergyModel body) {
        return super.update(id, body);
    }

    @ApiOperation(value = "Get an allergy by id", response = AllergyModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AllergyModel.class)})
    @Override
    public CompletableFuture<ResponseModel> getById(@PathVariable("id") Long id) {
        return super.getById(id);
    }

    @ApiOperation(value = "Get all allergies", response = AllergyModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = AllergyModel.class, responseContainer = "List")})
    @Override
    public CompletableFuture<ResponseModel> getAll() {
        return super.getAll();
    }
}

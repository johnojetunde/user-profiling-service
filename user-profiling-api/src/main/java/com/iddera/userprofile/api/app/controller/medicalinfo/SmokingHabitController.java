package com.iddera.userprofile.api.app.controller.medicalinfo;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.SmokingHabitModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.MedicalInfoService;
import com.iddera.userprofile.api.domain.model.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/smoking-habits")
public class SmokingHabitController extends AbstractMedicalInfoController<SmokingHabitModel> {
    public SmokingHabitController(MedicalInfoService<SmokingHabitModel> service) {
        super(service);
    }

    @PostMapping
    @ApiOperation(value = "Create a smoking-habit", response = SmokingHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = SmokingHabitModel.class)})
    @Override
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody SmokingHabitModel body,
                                                   @AuthenticationPrincipal User user) {
        return super.create(body, user);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update a smoking-habit", response = SmokingHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = SmokingHabitModel.class)})
    @Override
    public CompletableFuture<ResponseModel> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody SmokingHabitModel body,
                                                   @AuthenticationPrincipal User user) {
        return super.update(id, body, user);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "get a smoking-habit by id", response = SmokingHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = SmokingHabitModel.class)})
    @Override
    public CompletableFuture<ResponseModel> getById(@PathVariable("id") Long id,
                                                    @AuthenticationPrincipal User user) {
        return super.getById(id, user);
    }

    @GetMapping
    @ApiOperation(value = "get all user's smoking-habits", response = SmokingHabitModel.class)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = SmokingHabitModel.class, responseContainer = "List")})
    @Override
    public CompletableFuture<ResponseModel> getAll(@AuthenticationPrincipal User user) {
        return super.getAll(user);
    }
}

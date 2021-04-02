package com.iddera.userprofile.api.app.controller.medicalinfo;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.medicalinfo.model.MedicalForm;
import com.iddera.userprofile.api.domain.medicalinfo.service.MedicalFormService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("/medical-forms")
public class MedicalInfoController {
    private final MedicalFormService medicalFormService;

    @PostMapping
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalForm.class)})
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody MedicalForm body) {
        String username = "username";
        return medicalFormService.create(username, body)
                .thenApply(ResponseModel::new);
    }

    @GetMapping("/usernames/{username}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = MedicalForm.class)})
    public CompletableFuture<ResponseModel> getByUsername(@PathVariable("username") String username) {
        return medicalFormService.get(username)
                .thenApply(ResponseModel::new);
    }
}

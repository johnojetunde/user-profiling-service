package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.consultation.model.DoctorNoteModel;
import com.iddera.userprofile.api.domain.consultation.service.abstracts.DoctorNoteService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/doctor-note")
@RequiredArgsConstructor
public class DoctorNoteController {
    private final DoctorNoteService doctorNoteService;

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DoctorNoteModel.class)})
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody DoctorNoteModel request) {
        return doctorNoteService.create(request)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    @GetMapping("/{consultationId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DoctorNoteModel.class)})
    public CompletableFuture<ResponseModel> getNoteByConsultation(@PathVariable Long consultationId) {
        return doctorNoteService.findByConsultation(consultationId)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    @GetMapping("/{noteId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DoctorNoteModel.class)})
    public CompletableFuture<ResponseModel> getNote(@PathVariable Long noteId) {
        return doctorNoteService.findById(noteId)
                .thenApply(ResponseModel::new);
    }
}

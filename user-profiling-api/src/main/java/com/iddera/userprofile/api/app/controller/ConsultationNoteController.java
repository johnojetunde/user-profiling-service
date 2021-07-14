package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationNoteModel;
import com.iddera.userprofile.api.domain.consultation.service.abstracts.ConsultationNoteService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/notes")
@RequiredArgsConstructor
public class ConsultationNoteController {
    private final ConsultationNoteService consultationNoteService;

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ConsultationNoteModel.class)})
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody ConsultationNoteModel request) {
        return consultationNoteService.create(request)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','CLIENT')")
    @GetMapping("/{consultationId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ConsultationNoteModel.class)})
    public CompletableFuture<ResponseModel> getNotesByConsultation(@PathVariable Long consultationId) {
        return consultationNoteService.findByConsultation(consultationId)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR')")
    @GetMapping("/{noteId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ConsultationNoteModel.class)})
    public CompletableFuture<ResponseModel> getNote(@PathVariable Long noteId) {
        return consultationNoteService.findById(noteId)
                .thenApply(ResponseModel::new);
    }
}

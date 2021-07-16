package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationNoteModel;
import com.iddera.userprofile.api.domain.consultation.model.ConsultationNoteUpdateModel;
import com.iddera.userprofile.api.domain.consultation.service.abstracts.ConsultationNoteService;
import com.iddera.userprofile.api.domain.model.User;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody ConsultationNoteModel request
                                                    ,@AuthenticationPrincipal User user) {
        return consultationNoteService.create(request,user)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PutMapping( "/{id}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ConsultationNoteUpdateModel.class)})
    public CompletableFuture<ResponseModel> update(@PathVariable("id") Long id, @Valid @RequestBody ConsultationNoteUpdateModel request
            ,@AuthenticationPrincipal User user) {
        return consultationNoteService.update(id,request,user)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','CLIENT')")
    @GetMapping("/consultation/{consultationId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = ConsultationNoteModel.class)})
    public CompletableFuture<ResponseModel> getNotesByConsultation(@PathVariable Long consultationId) {
        return consultationNoteService.findNotesByConsultation(consultationId)
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

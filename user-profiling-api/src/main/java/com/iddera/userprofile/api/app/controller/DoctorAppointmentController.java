package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.domain.consultation.model.DoctorAppointmentModel;
import com.iddera.userprofile.api.domain.consultation.service.abstracts.DoctorAppointmentService;
import com.iddera.userprofile.api.domain.model.User;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/doctor-appointment")
@RequiredArgsConstructor
public class DoctorAppointmentController {
    private final DoctorAppointmentService doctorAppointmentService;

    @PreAuthorize("hasAuthority('CLIENT')")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/{doctorTimeSlotId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DoctorAppointmentModel.class)})
    public CompletableFuture<ResponseModel> create(@PathVariable Long doctorTimeSlotId, @AuthenticationPrincipal User user) {
        return doctorAppointmentService.create(doctorTimeSlotId,user)
                .thenApply(ResponseModel::new);
    }
}

package com.iddera.userprofile.api.app.controller;

import com.iddera.userprofile.api.app.model.ResponseModel;
import com.iddera.userprofile.api.app.model.TimeslotResult;
import com.iddera.userprofile.api.domain.consultation.model.DoctorTimeslotModel;
import com.iddera.userprofile.api.domain.consultation.model.Timeslot;
import com.iddera.userprofile.api.domain.consultation.model.TimeslotStatus;
import com.iddera.userprofile.api.domain.consultation.service.DoctorTimeslotService;
import com.iddera.userprofile.api.domain.model.User;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/timeslots")
@RequiredArgsConstructor
public class TimeslotController {

    private final DoctorTimeslotService timeslotService;

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DoctorTimeslotModel.class, responseContainer = "List")})
    public CompletableFuture<ResponseModel> create(@Valid @RequestBody Timeslot request,
                                                   @AuthenticationPrincipal User user) {
        return timeslotService.create(request, user)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','CLIENT','ADMIN')")
    @GetMapping(consumes = APPLICATION_JSON_VALUE, value = "/{timeslotId}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DoctorTimeslotModel.class)})
    public CompletableFuture<ResponseModel> getStatusById(@PathVariable("timeslotId") Long timeslotId) {
        return timeslotService.getById(timeslotId)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping(consumes = APPLICATION_JSON_VALUE, value = "/current/available")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = TimeslotResult.class)})
    public CompletableFuture<ResponseModel> getMyAvailableSlots(@RequestParam("date") LocalDate date,
                                                                @AuthenticationPrincipal User user,
                                                                @PageableDefault(sort = "id", direction = ASC) Pageable pageable) {
        return timeslotService.getMyAvailableSlots(date, user, pageable)
                .thenApply(TimeslotResult::new)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @GetMapping(consumes = APPLICATION_JSON_VALUE, value = "/current")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = TimeslotResult.class)})
    public CompletableFuture<ResponseModel> getAllMySlots(@RequestParam("date") LocalDate date,
                                                          @AuthenticationPrincipal User user,
                                                          @PageableDefault(sort = "id", direction = ASC) Pageable pageable) {
        return timeslotService.getMySlots(date, user, pageable)
                .thenApply(TimeslotResult::new)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','CLIENT','ADMIN')")
    @GetMapping(consumes = APPLICATION_JSON_VALUE, value = "/available")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = TimeslotResult.class)})
    public CompletableFuture<ResponseModel> getAvailableSlots(@RequestParam("date") LocalDate date,
                                                              @PageableDefault(sort = "id", direction = ASC) Pageable pageable) {
        return timeslotService.getAvailableSlots(date, pageable)
                .thenApply(TimeslotResult::new)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAnyAuthority('DOCTOR','CLIENT','ADMIN')")
    @GetMapping(consumes = APPLICATION_JSON_VALUE, value = "/doctors/{doctorId}/available")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = TimeslotResult.class)})
    public CompletableFuture<ResponseModel> getDoctorAvailableSlots(@PathVariable("doctorId") Long doctorId,
                                                                    @RequestParam("date") LocalDate date,
                                                                    @PageableDefault(sort = "id", direction = ASC) Pageable pageable) {
        return timeslotService.getDoctorAvailableSlots(doctorId, date, pageable)
                .thenApply(TimeslotResult::new)
                .thenApply(ResponseModel::new);
    }

    @PreAuthorize("hasAuthority('DOCTOR')")
    @PostMapping(consumes = APPLICATION_JSON_VALUE, value = "/{timeslotId}/status/{status}")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = DoctorTimeslotModel.class)})
    public CompletableFuture<ResponseModel> updateStatus(@PathVariable("timeslotId") Long timeslotId,
                                                         @PathVariable("status") TimeslotStatus status,
                                                         @AuthenticationPrincipal User user) {
        return timeslotService.updateStatus(timeslotId, status, user)
                .thenApply(ResponseModel::new);
    }
}

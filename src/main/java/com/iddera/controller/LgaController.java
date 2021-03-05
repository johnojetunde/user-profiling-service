package com.iddera.controller;

import com.iddera.exception.ApiException;
import com.iddera.model.dto.LocalGovernmentAreaDto;
import com.iddera.service.LgaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lgas")
@RequiredArgsConstructor
public class LgaController {
    private final LgaService lgaService;

    @GetMapping
    public Page<LocalGovernmentAreaDto> getLocalGovernmentArea(@RequestParam(required = false, defaultValue = "0") int page,
                                                               @RequestParam(required = false, defaultValue = "10") int size) {
        return lgaService.findAll(page, size);
    }

    @GetMapping("/{stateId}")
    public Page<LocalGovernmentAreaDto> getLocalGovernmentAreaByState(@PathVariable Long stateId,
                                                                        @RequestParam(required = false, defaultValue = "0") int page,
                                                                        @RequestParam(required = false, defaultValue = "10") int size) throws ApiException {
        return lgaService.findAllByState(page, size,stateId);
    }
}

package com.iddera.controller;
import com.iddera.exception.ApiException;
import com.iddera.model.dto.StateDto;
import com.iddera.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {
    private final StateService stateService;

    @GetMapping
    public Page<StateDto> getStates(@RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam(required = false, defaultValue = "10") int size) {
        return stateService.findAll(page, size);
    }

    @GetMapping("/{countryId}")
    public Page<StateDto> getStatesByCountry(@PathVariable long countryId,
                                    @RequestParam(required = false, defaultValue = "0") int page,
                                    @RequestParam(required = false, defaultValue = "10") int size) throws ApiException {

        return stateService.findAllByCountry(page, size,countryId);
    }
}

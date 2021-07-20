package com.iddera.userprofile.api.domain.medicalinfo.service.concretes;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.lab.model.HospitalModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.abstracts.HospitalService;
import com.iddera.userprofile.api.persistence.medicals.entity.Hospital;
import com.iddera.userprofile.api.persistence.medicals.repository.HospitalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.iddera.commons.utils.FunctionUtil.emptyIfNullStream;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultHospitalService implements HospitalService {
    private final HospitalRepository hospitalRepository;
    private final UserProfilingExceptionService exceptions;

    @Override
    public CompletableFuture<List<HospitalModel>> findAll() {
        return supplyAsync(() -> emptyIfNullStream(hospitalRepository.findAll())
                .map(Hospital::toModel)
                .collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<HospitalModel> findById(Long id) {
        return supplyAsync(() -> hospitalRepository.findById(id).orElseThrow(() ->
                exceptions.handleCreateNotFoundException("Hospital with id:%d  does not exist.", id)).toModel());
    }

    @Override
    public CompletableFuture<HospitalModel> update(HospitalModel request) {
        return supplyAsync(() -> {
            ensureHospitalWithNameAndAddressDoesNotExist(request.getName().strip(),request.getAddress().strip());
            Hospital hospital = new Hospital();
            if (request.getId() != null) {
                hospital = hospitalRepository.findById(request.getId())
                        .orElseGet(Hospital::new);
            }
            hospital.setName(request.getName())
                    .setAddress(request.getAddress());
            return hospitalRepository.save(hospital).toModel();
        });
    }

    private void ensureHospitalWithNameAndAddressDoesNotExist(String name, String address){
        if(hospitalRepository.existsByNameAndAddressIgnoreCase(name,address)){
            throw exceptions.handleCreateBadRequest("Hospital with name and address already exists.");
        }
    }
}

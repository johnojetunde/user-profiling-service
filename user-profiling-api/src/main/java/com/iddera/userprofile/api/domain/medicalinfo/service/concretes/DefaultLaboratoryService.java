package com.iddera.userprofile.api.domain.medicalinfo.service.concretes;

import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.lab.model.LaboratoryModel;
import com.iddera.userprofile.api.domain.medicalinfo.service.abstracts.LaboratoryService;
import com.iddera.userprofile.api.persistence.medicals.entity.Laboratory;
import com.iddera.userprofile.api.persistence.medicals.repository.LaboratoryRepository;
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
public class DefaultLaboratoryService implements LaboratoryService {
    private final LaboratoryRepository laboratoryRepository;
    private final UserProfilingExceptionService exceptions;

    @Override
    public CompletableFuture<List<LaboratoryModel>> findAll() {
        return supplyAsync(() -> emptyIfNullStream(laboratoryRepository.findAll())
                .map(Laboratory::toModel)
                .collect(Collectors.toList()));
    }

    @Override
    public CompletableFuture<LaboratoryModel> findById(Long id) {
        return supplyAsync(() -> laboratoryRepository.findById(id).orElseThrow(() ->
                exceptions.handleCreateNotFoundException("laboratory does not exist for %d", id)).toModel());
    }

    @Override
    public CompletableFuture<LaboratoryModel> update(LaboratoryModel request) {
        return supplyAsync(() -> {
            if (request.getId() == null)
                ensureEmailDoesNotExist(request.getContactInfo().getEmail());
            Laboratory laboratory = new Laboratory();
            if (request.getId() != null) {
                laboratory = laboratoryRepository.findById(request.getId())
                        .orElseGet(Laboratory::new);
            }
            laboratory.setName(request.getName())
                      .setAddress(request.getAddress())
                      .setContactInfo(request.getContactInfo())
                      .setAdditionalInfo(request.getAdditionalInfo())
                      .setWorkingHour(request.getWorkingHour())
                      .setSpecialisation(request.getSpecialisation());

            return laboratoryRepository.save(laboratory).toModel();
        });
    }


    private void ensureEmailDoesNotExist(String email){
        if(laboratoryRepository.existsByContactInfo_Email(email)){
            throw exceptions.handleCreateBadRequest("Email already exists for another laboratory.");
        }
    }
}

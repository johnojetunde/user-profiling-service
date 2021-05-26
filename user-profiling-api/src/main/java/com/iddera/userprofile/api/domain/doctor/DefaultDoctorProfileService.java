package com.iddera.userprofile.api.domain.doctor;

import com.iddera.userprofile.api.app.model.DoctorProfileUpdateRequest;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.DoctorProfileModel;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.persistence.doctorprofile.entity.DoctorProfile;
import com.iddera.userprofile.api.persistence.doctorprofile.repository.DoctorProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultDoctorProfileService implements DoctorProfileService {

    private final DoctorProfileRepository doctorProfileRepository;
    private final UserProfilingExceptionService exceptions;

    @Transactional
    @Override
    public CompletableFuture<DoctorProfileModel> update(Long userId, User user, DoctorProfileUpdateRequest request) {
        return supplyAsync(() -> {
            ensureProfileUpdateIsMadeByOwner(userId, user);

            DoctorProfile profile = doctorProfileRepository.findByUserId(userId)
                    .orElseGet(DoctorProfile::new);

            profile.setUserId(user.getId())
                    .setBio(request.getBio())
                    .setDesignation(request.getDesignation())
                    .setInterest(request.getInterest())
                    .setEducationInfo(request.getEducationInfo());

            return doctorProfileRepository.save(profile).toModel();
        });
    }

    @Override
    public CompletableFuture<DoctorProfileModel> get(User user) {
        return getById(user.getId());
    }

    @Override
    public CompletableFuture<DoctorProfileModel> get(Long userId) {
        return getById(userId);
    }


    private CompletableFuture<DoctorProfileModel> getById(Long userId) {
        return supplyAsync(() -> doctorProfileRepository.findByUserId(userId)
                .map(DoctorProfile::toModel)
                .orElseThrow(() -> exceptions.handleCreateNotFoundException("Doctor profile does not exist for %d", userId)));
    }

    private void ensureProfileUpdateIsMadeByOwner(Long userId, User user) {
        if (!userId.equals(user.getId())) {
            throw exceptions.handleCreateForbidden("Doctor is not allowed to update another doctor's profile");
        }
    }
}

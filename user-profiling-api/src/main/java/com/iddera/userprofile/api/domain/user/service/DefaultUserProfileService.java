package com.iddera.userprofile.api.domain.user.service;

import com.iddera.userprofile.api.app.model.UserProfileUpdateRequest;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.domain.user.model.UserProfileModel;
import com.iddera.userprofile.api.persistence.userprofile.entity.LocalGovernmentArea;
import com.iddera.userprofile.api.persistence.userprofile.entity.UserProfile;
import com.iddera.userprofile.api.persistence.userprofile.repository.LgaRepository;
import com.iddera.userprofile.api.persistence.userprofile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultUserProfileService implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final LgaRepository lgaRepository;
    private final UserProfilingExceptionService exceptions;

    @Transactional
    @Override
    public CompletableFuture<UserProfileModel> update(Long userId, User user, UserProfileUpdateRequest request) {
        return supplyAsync(() -> {
            ensureProfileUpdateIsMadeByOwner(userId, user);

            UserProfile profile = userProfileRepository.findByUserId(userId)
                    .orElseGet(UserProfile::new);

            profile.setUserId(user.getId())
                    .setMaritalStatus(request.getMaritalStatus())
                    .setGender(request.getGender())
                    .setLga(getLga(request.getLgaId()))
                    .setDateOfBirth(request.getDateOfBirth());

            return userProfileRepository.save(profile).toModel();
        });
    }

    @Override
    public CompletableFuture<UserProfileModel> get(User user) {
        return getById(user.getId());
    }

    @Override
    public CompletableFuture<UserProfileModel> get(Long userId) {
        return getById(userId);
    }

    private LocalGovernmentArea getLga(Long lgaId) {
        return lgaRepository.findById(lgaId)
                .orElseThrow(() -> exceptions.handleCreateBadRequest(format("Local government area with id:%d does not exist.", lgaId)));
    }

    private CompletableFuture<UserProfileModel> getById(Long userId) {
        return supplyAsync(() -> userProfileRepository.findByUserId(userId)
                .map(UserProfile::toModel)
                .orElseThrow(() -> exceptions.handleCreateNotFoundException("User profile does not exist for %d", userId)));
    }

    private void ensureProfileUpdateIsMadeByOwner(Long userId, User user) {
        if (!userId.equals(user.getId())) {
            throw exceptions.handleCreateForbidden("User is not allowed to update another user's profile");
        }
    }
}

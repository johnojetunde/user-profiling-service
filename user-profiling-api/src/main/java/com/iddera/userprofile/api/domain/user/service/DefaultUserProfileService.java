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

import java.util.Optional;
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
    public CompletableFuture<UserProfileModel> update(String username, User user, UserProfileUpdateRequest request) {
        return supplyAsync(() -> {
            ensureProfileUpdateIsMadeByOwner(username, user);

            UserProfile profile = userProfileRepository.findByUsernameIgnoreCase(username)
                    .orElseGet(UserProfile::new);

            profile.setUsername(user.getUsername())
                    .setMaritalStatus(request.getMaritalStatus())
                    .setGender(request.getGender())
                    .setLga(getLga(request.getLgaId()))
                    .setDateOfBirth(request.getDateOfBirth());

            return userProfileRepository.save(profile).toModel();
        });
    }

    @Transactional
    public CompletableFuture<UserProfileModel> create(String username, UserProfileUpdateRequest request) {
        return supplyAsync(() -> {
            var isProfileExist = userProfileRepository.existsUserProfileByUsernameIgnoreCase(username);
            if (isProfileExist) {
                throw exceptions.handleCreateBadRequest("An account has been previously created for this user");
            }

            var profile = new UserProfile()
                    .setUsername(username)
                    .setMaritalStatus(request.getMaritalStatus())
                    .setGender(request.getGender())
                    .setLga(getLga(request.getLgaId()))
                    .setDateOfBirth(request.getDateOfBirth());

            return userProfileRepository.save(profile).toModel();
        });
    }

    @Override
    public CompletableFuture<UserProfileModel> get(String username) {
        return supplyAsync(() -> userProfileRepository.findByUsernameIgnoreCase(username)
                .map(UserProfile::toModel)
                .orElseThrow(() -> exceptions.handleCreateNotFoundException("User profile does not exist for %s", username)));

    }

    private LocalGovernmentArea getLga(Long lgaId) {
        return Optional.ofNullable(lgaId)
                .flatMap(lgaRepository::findById)
                .orElseThrow(() -> exceptions.handleCreateBadRequest(format("Local government area with id:%d does not exist.", lgaId)));
    }

    private void ensureProfileUpdateIsMadeByOwner(String username, User user) {
        if (!username.equals(user.getUsername())) {
            throw exceptions.handleCreateForbidden("User is not allowed to update another user's profile");
        }
    }
}

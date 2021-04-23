package com.iddera.userprofile.api.domain.userprofile.service;

import com.iddera.userprofile.api.app.model.UserProfileRequest;
import com.iddera.userprofile.api.app.model.UserProfileUpdateRequest;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.userprofile.model.UserProfileModel;
import com.iddera.userprofile.api.persistence.userprofile.entity.Country;
import com.iddera.userprofile.api.persistence.userprofile.entity.LocalGovernmentArea;
import com.iddera.userprofile.api.persistence.userprofile.entity.State;
import com.iddera.userprofile.api.persistence.userprofile.entity.UserProfile;
import com.iddera.userprofile.api.persistence.userprofile.repository.CountryRepository;
import com.iddera.userprofile.api.persistence.userprofile.repository.LgaRepository;
import com.iddera.userprofile.api.persistence.userprofile.repository.StateRepository;
import com.iddera.userprofile.api.persistence.userprofile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static java.lang.String.format;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final LgaRepository lgaRepository;
    private final UserProfilingExceptionService exceptions;

    @Override
    public CompletableFuture<UserProfileModel> create(UserProfileRequest request) {
        return createEntity(request).thenApply(UserProfile::toModel);
    }

    @Override
    public CompletableFuture<UserProfileModel> update(Long userId, UserProfileUpdateRequest request) {
        return updateEntity(userId, request)
                .thenApply(UserProfile::toModel);
    }

    @Transactional
    public CompletableFuture<UserProfile> updateEntity(Long userId, UserProfileUpdateRequest request) {
        return supplyAsync(() -> {
            UserProfile profile = getExistingUserProfile(userId,
                    () -> exceptions.handleCreateBadRequest(format("User profile for userId :%d does not exist.", userId)));
            profile
                    .setMaritalStatus(request.getMaritalStatus())
                    .setGender(request.getGender())
                    .setCountry(getCountryExists(request.getLocation().getCountryId()))
                    .setState(getStateExists(request.getLocation().getStateId()))
                    .setLga(getLgaExists(request.getLocation().getLgaId()));

            return userProfileRepository.save(profile);
        });
    }

    @Transactional
    public CompletableFuture<UserProfile> createEntity(UserProfileRequest request) {
        return supplyAsync(() -> {
            ensureUserExists(request.getUserId());
            ensureUserProfileDoesNotExist(request.getUserId());

            UserProfile userProfile = new UserProfile()
                    .setUserId(request.getUserId())
                    .setMaritalStatus(request.getMaritalStatus())
                    .setGender(request.getGender())
                    .setCountry(getCountryExists(request.getLocation().getCountryId()))
                    .setState(getStateExists(request.getLocation().getStateId()))
                    .setLga(getLgaExists(request.getLocation().getLgaId()));

            return userProfileRepository.save(userProfile);
        });
    }

    public UserProfile getExistingUserProfile(Long userId, Supplier<RuntimeException> exceptionSupplier) {
        var userProfileResult = userProfileRepository.findByUserId(userId);
        return userProfileResult
                .orElseThrow(exceptionSupplier);
    }

    public Country getCountryExists(Long countryId) {
        return countryRepository.findById(countryId)
                .orElseThrow(() -> exceptions.handleCreateBadRequest(format("Country with id:%d does not exist.", countryId)));
    }

    public State getStateExists(Long stateId) {
        return stateRepository.findById(stateId)
                .orElseThrow(() -> exceptions.handleCreateBadRequest(format("State with id:%d does not exist.", stateId)));
    }

    public LocalGovernmentArea getLgaExists(Long lgaId) {
        return lgaRepository.findById(lgaId)
                .orElseThrow(() -> exceptions.handleCreateBadRequest(format("Local government area with id:%d does not exist.", lgaId)));
    }

    public void ensureUserProfileDoesNotExist(Long userId) {
        boolean profileExists = userProfileRepository.existsByUserId(userId);
        if (profileExists) {
            throw exceptions.handleCreateBadRequest(String.format("Profile already exists for userId :%d", userId));
        }
    }

    //TODO: Make call to user management service to get the user's details.
    public void ensureUserExists(Long userId) {
    }

}

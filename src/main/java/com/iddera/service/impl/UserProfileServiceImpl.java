package com.iddera.service.impl;

import com.iddera.entity.Country;
import com.iddera.entity.LocalGovernmentArea;
import com.iddera.entity.State;
import com.iddera.entity.UserProfile;
import com.iddera.model.UserProfileModel;
import com.iddera.model.request.UserProfileRequest;
import com.iddera.model.request.UserProfileUpdateRequest;
import com.iddera.repository.CountryRepository;
import com.iddera.repository.LgaRepository;
import com.iddera.repository.StateRepository;
import com.iddera.repository.UserProfileRepository;
import com.iddera.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static com.iddera.util.ExceptionUtil.handleCreateBadRequest;
import static java.lang.String.format;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Service
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final LgaRepository lgaRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository,
                                  CountryRepository countryRepository,
                                  StateRepository stateRepository,
                                  LgaRepository lgaRepository) {
        this.userProfileRepository = userProfileRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.lgaRepository = lgaRepository;
    }

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
                    () -> handleCreateBadRequest(format("User profile for userId :%d does not exist.", userId)));
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
                .orElseThrow(() -> handleCreateBadRequest(format("Country with id:%d does not exist.", countryId)));
    }

    public State getStateExists(Long stateId) {
        return stateRepository.findById(stateId)
                .orElseThrow(() -> handleCreateBadRequest(format("State with id:%d does not exist.", stateId)));
    }

    public LocalGovernmentArea getLgaExists(Long lgaId) {
        return lgaRepository.findById(lgaId)
                .orElseThrow(() -> handleCreateBadRequest(format("Local government area with id:%d does not exist.", lgaId)));
    }

    public void ensureUserProfileDoesNotExist(Long userId) {
        boolean profileExists = userProfileRepository.existsByUserId(userId);
        if (profileExists) {
            throw handleCreateBadRequest(String.format("Profile already exists for userId :%d", userId));
        }
    }

    //TODO: Make call to user management service to get the user's details.
    public void ensureUserExists(Long userId) {
    }

}

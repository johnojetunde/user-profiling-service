package com.iddera.service.impl;

import com.iddera.entity.Country;
import com.iddera.entity.LocalGovernmentArea;
import com.iddera.entity.State;
import com.iddera.entity.UserProfile;
import com.iddera.exception.ApiException;
import com.iddera.exception.UserProfilingException;
import com.iddera.model.UserProfileModel;
import com.iddera.model.request.UserProfileRequest;
import com.iddera.repository.CountryRepository;
import com.iddera.repository.LgaRepository;
import com.iddera.repository.StateRepository;
import com.iddera.repository.UserProfileRepository;
import com.iddera.service.UserProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<UserProfileModel> create(UserProfileRequest request){
        if(request.getUserId() == null)
            throw new UserProfilingException("User Id can not be null.");
        return createEntity(request).thenApply(UserProfile::toModel);
    }

    @Override
    public CompletableFuture<UserProfileModel> update(Long userId, UserProfileRequest request){
        return updateEntity(userId,request).thenApply(UserProfile::toModel);
    }

    @Transactional
    public CompletableFuture<UserProfile> updateEntity(Long userId, UserProfileRequest request) {
        return supplyAsync(() -> {
            UserProfile profile = getExistingUserProfile(userId);
                                     profile
                                    .setMaritalStatus(request.getMaritalStatus())
                                    .setGender(request.getGender())
                                    .setCountry(ensureCountryExists(request.getCountryId()))
                                    .setState(ensureStateExists(request.getStateId()))
                                    .setLga(ensureLgaExists(request.getLgaId()));

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
                                      .setCountry(ensureCountryExists(request.getUserId()))
                                      .setState(ensureStateExists(request.getUserId()))
                                      .setLga(ensureLgaExists(request.getUserId()));

            return userProfileRepository.save(userProfile);
        });
    }

    public UserProfile getExistingUserProfile(Long userId){
        var userProfileResult =  userProfileRepository.findByUserId(userId);
        return userProfileResult
                .orElseThrow(() -> new ApiException(format("User profile for userId :%d does not exist.",userId)));
    }

    public Country ensureCountryExists(Long countryId) {
        return countryRepository.findById(countryId)
                .orElseThrow(() -> new ApiException(String.format("Country with id :%d does not exist.", countryId)));
    }

    public State ensureStateExists(Long stateId){
        return stateRepository.findById(stateId)
                .orElseThrow(() -> new ApiException(String.format("State with id :%d does not exist.",stateId)));
    }

    public LocalGovernmentArea ensureLgaExists(Long lgaId){
        return lgaRepository.findById(lgaId)
                .orElseThrow(() -> new ApiException(String.format("Local government area with id :%d does not exist.",lgaId)));
    }

    public void ensureUserProfileDoesNotExist(Long userId){
        boolean profileExists = userProfileRepository.existsByUserId(userId);
        if(profileExists){
            throw new ApiException(String.format("Profile already exists for userId :%d",userId));
        }
    }

    //TODO: Make call to user management service to get the user's details.
    public void ensureUserExists(Long userId){
    }

}

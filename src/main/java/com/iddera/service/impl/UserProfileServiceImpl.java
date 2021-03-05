package com.iddera.service.impl;

import com.iddera.entity.UserProfile;
import com.iddera.exception.UserProfilingException;
import com.iddera.model.UserProfileModel;
import com.iddera.model.request.UserProfileRequest;
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

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public CompletableFuture<UserProfileModel> create(UserProfileRequest request){
        if(request.getUserId() == null)
            throw new UserProfilingException("UserId can not be null.");
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
                                    .setLocation(request.getLocation());

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
                                      .setLocation(request.getLocation());

            return userProfileRepository.save(userProfile);
        });
    }

    public UserProfile getExistingUserProfile(Long userId){
        var userProfileResult =  userProfileRepository.findByUserId(userId);
        return userProfileResult
                .orElseThrow(() -> new UserProfilingException(format("User profile for userId :%d does not exist.",userId)));
    }

    public void ensureUserProfileDoesNotExist(Long userId){
        boolean profileExists = userProfileRepository.existsByUserId(userId);
        if(profileExists){
            throw new UserProfilingException(String.format("Profile already exists for userId :%d",userId));
        }
    }

    //TODO: Make call to user management service to get the user's details.
    public void ensureUserExists(Long userId){
    }

}

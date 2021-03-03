package com.iddera.service.impl;

import com.google.common.base.Strings;
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
    //private final Executor executor;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public CompletableFuture<UserProfileModel> create(UserProfileRequest request){
        if(Strings.isNullOrEmpty(request.getUsername()))
            throw new UserProfilingException("Username can not be null or empty");
        return createEntity(request).thenApply(UserProfile::toModel);
    }

    @Override
    public CompletableFuture<UserProfileModel> update(String username, UserProfileRequest request){
        return updateEntity(username,request).thenApply(UserProfile::toModel);
    }

    @Transactional
    public CompletableFuture<UserProfile> updateEntity(String username, UserProfileRequest request) {
        return supplyAsync(() -> {
            UserProfile profile = ensureUserProfileExists(username);
                                     profile
                                    .setUsername(request.getUsername())
                                    .setMaritalStatus(request.getMaritalStatus())
                                    .setGender(request.getGender())
                                    .setLocation(request.getLocation());

            return userProfileRepository.save(profile);
        });
    }

    @Transactional
    public CompletableFuture<UserProfile> createEntity(UserProfileRequest request) {
            return supplyAsync(() -> {
            ensureUserExists(request.getUsername());
            ensureUserProfileDoesNotExist(request.getUsername());

            UserProfile userProfile = new UserProfile()
                                      .setUsername(request.getUsername())
                                      .setMaritalStatus(request.getMaritalStatus())
                                      .setGender(request.getGender())
                                      .setLocation(request.getLocation());

            return userProfileRepository.save(userProfile);
        });
    }

    public UserProfile ensureUserProfileExists(String username){
        var userProfileResult =  userProfileRepository.findByUsername(username);
        return userProfileResult
                .orElseThrow(() -> new UserProfilingException(format("User profile for user :%s does not exist.",username)));
    }

    public void ensureUserProfileDoesNotExist(String username){
        boolean profileExists = userProfileRepository.existsByUsername(username);
        if(profileExists){
            throw new UserProfilingException(String.format("Profile already exists for user :%s",username));
        }
    }

    //TODO: Make call to user management service to get the user's details.
    public void ensureUserExists(String username){
    }

}

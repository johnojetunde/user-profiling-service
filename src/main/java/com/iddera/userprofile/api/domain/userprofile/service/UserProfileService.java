package com.iddera.userprofile.api.domain.userprofile.service;

import com.iddera.userprofile.api.app.model.UserProfileRequest;
import com.iddera.userprofile.api.app.model.UserProfileUpdateRequest;
import com.iddera.userprofile.api.domain.userprofile.model.UserProfileModel;

import java.util.concurrent.CompletableFuture;

public interface UserProfileService {
    CompletableFuture<UserProfileModel> create(UserProfileRequest request);

    CompletableFuture<UserProfileModel> update(Long userId, UserProfileUpdateRequest request);
}

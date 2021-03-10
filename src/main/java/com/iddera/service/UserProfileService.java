package com.iddera.service;

import com.iddera.model.UserProfileModel;
import com.iddera.model.request.UserProfileRequest;
import com.iddera.model.request.UserProfileUpdateRequest;

import java.util.concurrent.CompletableFuture;

public interface UserProfileService {
    CompletableFuture<UserProfileModel> create(UserProfileRequest request);
    CompletableFuture<UserProfileModel> update(Long userId, UserProfileUpdateRequest request);
}

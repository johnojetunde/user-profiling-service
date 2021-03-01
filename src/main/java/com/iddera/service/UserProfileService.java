package com.iddera.service;

import com.iddera.model.UserProfileModel;
import com.iddera.model.request.UserProfileRequest;

import java.util.concurrent.CompletableFuture;

public interface UserProfileService {
    CompletableFuture<UserProfileModel> create(UserProfileRequest request);
    CompletableFuture<UserProfileModel> update(long userId, UserProfileRequest request);
}

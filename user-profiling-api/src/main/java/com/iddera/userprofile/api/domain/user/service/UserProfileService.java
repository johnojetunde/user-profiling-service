package com.iddera.userprofile.api.domain.user.service;

import com.iddera.userprofile.api.app.model.UserProfileUpdateRequest;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.domain.user.model.UserProfileModel;

import java.util.concurrent.CompletableFuture;

public interface UserProfileService {
    CompletableFuture<UserProfileModel> update(Long userId, User user, UserProfileUpdateRequest request);

    CompletableFuture<UserProfileModel> get(User user);

    CompletableFuture<UserProfileModel> get(Long userId);
}

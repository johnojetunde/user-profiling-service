package com.iddera.userprofile.api.domain.user.service;

import com.iddera.userprofile.api.app.model.NewClientModel;
import com.iddera.userprofile.api.app.model.UserProfileUpdateRequest;
import com.iddera.userprofile.api.domain.model.User;
import com.iddera.userprofile.api.domain.user.model.UserProfileModel;

import java.util.concurrent.CompletableFuture;

public interface UserProfileService {
    CompletableFuture<UserProfileModel> update(String username, User user, UserProfileUpdateRequest request);

    CompletableFuture<UserProfileModel> create(String username, UserProfileUpdateRequest request);

    default CompletableFuture<UserProfileModel> create(String username, NewClientModel requestModel) {
        var request = new UserProfileUpdateRequest()
                .setGender(requestModel.getGender())
                .setMaritalStatus(requestModel.getMaritalStatus())
                .setDateOfBirth(requestModel.getDateOfBirth())
                .setLgaId(requestModel.getLgaId());

        return create(username, request);
    }


    default CompletableFuture<UserProfileModel> get(User user) {
        return get(user.getUsername());
    }

    CompletableFuture<UserProfileModel> get(String username);
}

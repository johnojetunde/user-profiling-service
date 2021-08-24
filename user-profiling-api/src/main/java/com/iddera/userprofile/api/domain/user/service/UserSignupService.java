package com.iddera.userprofile.api.domain.user.service;

import com.iddera.usermanagement.lib.domain.model.UserModel;
import com.iddera.usermanagement.lib.domain.model.UserType;
import com.iddera.userprofile.api.app.model.NewClientModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class UserSignupService {

    private final UserProfileService userProfileService;
    private final UserService userService;

    public CompletableFuture<UserModel> createClient(NewClientModel clientModel) {
        return userService.createUser(clientModel, UserType.CLIENT)
                .thenCompose(userModel ->
                        userProfileService.create(userModel.getUsername(), clientModel)
                                .thenApply(__ -> userModel));
    }
}

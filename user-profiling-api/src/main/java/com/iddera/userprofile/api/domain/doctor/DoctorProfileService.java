package com.iddera.userprofile.api.domain.doctor;

import com.iddera.userprofile.api.app.model.DoctorProfileUpdateRequest;
import com.iddera.userprofile.api.domain.model.DoctorProfileModel;
import com.iddera.userprofile.api.domain.model.User;

import java.util.concurrent.CompletableFuture;

public interface DoctorProfileService {
    CompletableFuture<DoctorProfileModel> update(Long userId, User user, DoctorProfileUpdateRequest request);

    CompletableFuture<DoctorProfileModel> get(User user);

    CompletableFuture<DoctorProfileModel> get(Long userId);
}

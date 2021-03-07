package com.iddera.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileRequest extends UserProfileUpdateRequest{
    @NotNull
    private Long userId;
}

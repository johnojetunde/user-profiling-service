package com.iddera.userprofile.api.app.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshToken {
    @NotBlank
    private String refreshToken;
}

package com.iddera.userprofile.api.app.model;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class LoginResponse {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private Long expiresIn;
    private String scope;
    private String jti;
}

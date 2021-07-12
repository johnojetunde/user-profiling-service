package com.iddera.userprofile.api.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iddera.usermanagement.lib.domain.model.RoleModel;
import com.iddera.usermanagement.lib.domain.model.UserModel;
import com.iddera.usermanagement.lib.domain.model.UserType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.persistence.Transient;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Accessors(chain = true)
@Data
@Component
@RequiredArgsConstructor
public class User implements UserDetails {
    private Long id;
    private String username;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private UserType userType;

    @JsonIgnore
    @Transient
    private String password;

    public User(Long id,
                String username,
                String email,
                String password,
                Collection<? extends GrantedAuthority> authorities,
                UserType userType) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.userType = userType;
    }

    public static User build(UserModel user, String token) {
        List<GrantedAuthority> authorities = user.getRole().stream()
                .map(RoleModel::getName)
                .map(String::toUpperCase)
                .map(SimpleGrantedAuthority::new)
                .collect(toList());

        return new User(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                token,
                authorities,
                user.getType());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

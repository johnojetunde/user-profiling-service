package com.iddera.config;

import com.iddera.enums.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
//        Optional<String> loggedInUser = Optional.ofNullable(SecurityContextHolder.getContext())
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(this::getUsername);

//        return Optional.of(loggedInUser.orElse(UserType.SYSTEM.toString()));
          return Optional.of(UserType.SYSTEM.toString());
    }

//    private String getUsername(Authentication authentication) {
//        if (authentication.getPrincipal() instanceof String) {
//            return (String) authentication.getPrincipal();
//        }
//
//        var user = (User) authentication.getPrincipal();
//        return user.getUsername();
//    }
}

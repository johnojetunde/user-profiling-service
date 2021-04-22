package com.iddera.userprofile.api.app.config;

import com.iddera.client.model.ResponseModel;
import com.iddera.commons.utils.TokenExtractor;
import com.iddera.usermanagement.client.UserManagementClient;
import com.iddera.usermanagement.lib.domain.model.UserModel;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class Filter extends OncePerRequestFilter {
    private final UserManagementClient userManagementClient;
    private final TokenExtractor tokenExtractor;
    private final UserProfilingExceptionService exceptionService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = extractToken(authorizationHeader);

        ResponseModel<UserModel> user = userManagementClient.users().getUserDetails(token).join();
        if(user.isSuccessful()){
            UserDetails userDetails = UserDetailsImpl.build(user.getData());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else{
            logger.error("Invalid credentials.");
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Invalid credentials.");
            return;
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    private String extractToken(String authorizationHeader){
        return tokenExtractor.extractToken(authorizationHeader)
                .orElseThrow(() -> exceptionService.handleCreateUnAuthorized("User authentication not successful"));
    }
}

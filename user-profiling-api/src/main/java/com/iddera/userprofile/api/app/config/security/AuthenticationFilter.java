package com.iddera.userprofile.api.app.config.security;

import com.iddera.client.model.ResponseModel;
import com.iddera.commons.utils.TokenExtractor;
import com.iddera.usermanagement.client.UserManagementClient;
import com.iddera.usermanagement.lib.domain.model.UserModel;
import com.iddera.userprofile.api.domain.exception.UserProfilingExceptionService;
import com.iddera.userprofile.api.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private final UserManagementClient userManagementClient;
    private final TokenExtractor tokenExtractor;
    private final UserProfilingExceptionService exceptionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            Optional<String> tokenOptional = extractToken(authorizationHeader);
            if (tokenOptional.isPresent()) {
                var token = tokenOptional.get();
                ResponseModel<UserModel> user = userManagementClient.users().getUserDetails(token).join();
                if (!user.isSuccessful()) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials.");
                    return;
                }

                UserDetails userDetails = User.build(user.getData(), token);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, token, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials.");
        }
    }

    private Optional<String> extractToken(String authorizationHeader) {
        return tokenExtractor.extractToken(authorizationHeader);
    }
}

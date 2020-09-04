package io.tam.ssc1.security.filter;

import io.tam.ssc1.security.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFilter
        extends OncePerRequestFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void doFilterInternal(HttpServletRequest request,
                         HttpServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        // accept request header
        String authorization = request.getHeader("Authorization");

        // make new instance of CustomAuthentication
        CustomAuthentication customAuthentication = new CustomAuthentication(authorization, null);

        try {
            //authenticate customAuthentication and store in result
            Authentication result = authenticationManager.authenticate(customAuthentication);

            // (result should be present without this check)
            if (result.isAuthenticated()) {
                // put result information in security context
                SecurityContextHolder.getContext().setAuthentication(result);
                chain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }

        } catch (AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}

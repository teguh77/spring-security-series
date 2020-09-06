package io.tam.ssc1.security.filter;

import io.tam.ssc1.entities.Otp;
import io.tam.ssc1.repositories.OtpRepository;
import io.tam.ssc1.security.authentication.OtpAuthentication;
import io.tam.ssc1.security.authentication.UsernamePasswordAuthentication;
import io.tam.ssc1.security.manager.TokenManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class UsernamePasswordAuthFilter
        extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final OtpRepository otpRepository;
    private final TokenManager tokenManager;

    public UsernamePasswordAuthFilter(AuthenticationManager authenticationManager,
                                      OtpRepository otpRepository,
                                      TokenManager tokenManager) {
        this.authenticationManager = authenticationManager;
        this.otpRepository = otpRepository;
        this.tokenManager = tokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String username = request.getHeader("username");
        String password = request.getHeader("password");
        String otp = request.getHeader("otp");

        if(otp == null) {
            UsernamePasswordAuthentication a = new UsernamePasswordAuthentication(username, password);
            authenticationManager.authenticate(a);

            String code = String.valueOf(new Random().nextInt(9999) + 1000);

            Otp otpEntity = new Otp();
            otpEntity.setUsername(username);
            otpEntity.setOtp(code);

            otpRepository.save(otpEntity);
        } else {
            OtpAuthentication a = new OtpAuthentication(username, otp);
            try {
                authenticationManager.authenticate(a);

                String token = UUID.randomUUID().toString();
                tokenManager.add(token);
                response.setHeader("Authorization", token);
            } catch (AuthenticationException e) {
               response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }
}

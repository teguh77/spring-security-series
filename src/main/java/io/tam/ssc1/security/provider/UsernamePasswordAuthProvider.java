package io.tam.ssc1.security.provider;

import io.tam.ssc1.security.authentication.UsernamePasswordAuthentication;
import io.tam.ssc1.services.JpaUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsernamePasswordAuthProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final JpaUserDetailsService jpaUserDetailsService;

    public UsernamePasswordAuthProvider(PasswordEncoder passwordEncoder,
                                        JpaUserDetailsService jpaUserDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.jpaUserDetailsService = jpaUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = jpaUserDetailsService.loadUserByUsername(username);

        if(passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthentication(username, password, user.getAuthorities());
        }

        throw new BadCredentialsException("Username Password :(");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthentication.class.equals(aClass);
    }
}

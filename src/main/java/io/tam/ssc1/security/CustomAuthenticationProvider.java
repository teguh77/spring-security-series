package io.tam.ssc1.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider
    implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService,
                                        PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String username = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        // load user dari inMemory
        UserDetails user = userDetailsService.loadUserByUsername(username);
        if(user != null) {
            if(passwordEncoder.matches(password, user.getPassword())) {

                return new UsernamePasswordAuthenticationToken(
                        username,
                        password,
                        user.getAuthorities()
                );
            }
        }

        throw new BadCredentialsException("Error!");
    }

    @Override
    public boolean supports(Class<?> authType) {
        // methode ini dipanggil sebelum authenticate methode
        // mengecek apakah type authentication yang digunakan sesuai
        return UsernamePasswordAuthenticationToken.class.equals(authType);
    }
}

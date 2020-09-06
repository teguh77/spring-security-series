package io.tam.ssc1.security.provider;

import io.tam.ssc1.security.authentication.TokenAuthentication;
import io.tam.ssc1.security.manager.TokenManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class TokenAuthProvider implements AuthenticationProvider {

    private final TokenManager tokenManager;

    public TokenAuthProvider(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getPrincipal().toString();
        boolean isExist = tokenManager.contains(token);
        if(isExist) {
            return new TokenAuthentication(token, null, null);
        }
        throw new BadCredentialsException("token :(");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return TokenAuthentication.class.equals(aClass);
    }
}

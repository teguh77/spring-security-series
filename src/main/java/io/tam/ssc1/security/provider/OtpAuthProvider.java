package io.tam.ssc1.security.provider;

import io.tam.ssc1.entities.Otp;
import io.tam.ssc1.repositories.OtpRepository;
import io.tam.ssc1.security.authentication.OtpAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;
import java.util.Optional;

public class OtpAuthProvider
        implements AuthenticationProvider {

    private final OtpRepository otpRepository;

    public OtpAuthProvider(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String otp = authentication.getCredentials().toString();

        Optional<Otp> optionalOtp = otpRepository.findByOtp(otp);

        if(optionalOtp.isPresent()){
            return new OtpAuthentication(username, otp, List.of(() -> "read"));
        }

        throw new BadCredentialsException("Otp :(");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return OtpAuthentication.class.equals(aClass);
    }
}

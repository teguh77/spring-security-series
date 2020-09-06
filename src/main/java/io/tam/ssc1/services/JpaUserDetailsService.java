package io.tam.ssc1.services;

import io.tam.ssc1.entities.User;
import io.tam.ssc1.repositories.UserRepository;
import io.tam.ssc1.security.model.AppUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s Not found", username)));

        return new AppUserDetails(user);
    }
}

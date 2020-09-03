package io.tam.ssc1.services;

import io.tam.ssc1.entities.User;
import io.tam.ssc1.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class JPAUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JPAUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("Username not found Error"));

        return new AppUserDetails(user);
    }
}

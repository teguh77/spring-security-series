package io.tam.ssc1.config;

import io.tam.ssc1.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig
        extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager uds = new InMemoryUserDetailsManager();
        UserDetails user = User.withUsername("john")
                        .password("pass")
                        .authorities("read")
                        .build();
        uds.createUser(user);

        return uds;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // bisa chaining authenticationprovider lebih dari satu
        auth.authenticationProvider(customAuthenticationProvider);
    }
}

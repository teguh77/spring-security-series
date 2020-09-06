package io.tam.ssc1.config;

import io.tam.ssc1.repositories.OtpRepository;
import io.tam.ssc1.security.filter.TokenAuthFilter;
import io.tam.ssc1.security.filter.UsernamePasswordAuthFilter;
import io.tam.ssc1.security.manager.TokenManager;
import io.tam.ssc1.security.provider.OtpAuthProvider;
import io.tam.ssc1.security.provider.TokenAuthProvider;
import io.tam.ssc1.security.provider.UsernamePasswordAuthProvider;
import io.tam.ssc1.services.JpaUserDetailsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableAsync
public class AppConfig
        extends WebSecurityConfigurerAdapter{

    private final JpaUserDetailsService jpaUserDetailsService;
    private final OtpRepository otpRepository;
    private final TokenManager tokenManager;

    public AppConfig(JpaUserDetailsService jpaUserDetailsService,
                     OtpRepository otpRepository,
                     TokenManager tokenManager) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.otpRepository = otpRepository;
        this.tokenManager = tokenManager;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new UsernamePasswordAuthProvider(passwordEncoder(), jpaUserDetailsService))
                .authenticationProvider(new OtpAuthProvider(otpRepository))
                .authenticationProvider(new TokenAuthProvider(tokenManager));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterAt(new UsernamePasswordAuthFilter(
                                authenticationManagerBean(),
                                otpRepository,
                                tokenManager
                        ),
                        BasicAuthenticationFilter.class)
                .addFilterAfter(new TokenAuthFilter(
                        authenticationManagerBean()
                        ),
                        BasicAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public InitializingBean initializingBean() {
        return () -> SecurityContextHolder
                .setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
    }
}

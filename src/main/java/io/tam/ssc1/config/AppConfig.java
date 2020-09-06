package io.tam.ssc1.config;

import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;
import java.util.concurrent.Callable;

@Configuration
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .anyRequest()
                .permitAll();

        http.cors(c -> {
            CorsConfigurationSource  cs = r -> {
                CorsConfiguration cc = new CorsConfiguration();
                cc.setAllowedOrigins(List.of("*"));
                cc.setAllowedMethods(List.of("GET", "POST"));
                // lebih baik spesify value of list dengan setting environtment variable
                return cc;
            };
            c.configurationSource(cs);
        });

    }
}

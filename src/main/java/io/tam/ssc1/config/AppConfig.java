package io.tam.ssc1.config;

import io.tam.ssc1.security.CustomCsrfFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
public class AppConfig extends WebSecurityConfigurerAdapter {

    private final CustomCsrfFilter customCsrfFilter;

    public AppConfig(CustomCsrfFilter customCsrfFilter) {
        this.customCsrfFilter = customCsrfFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
//        http.csrf().disable();
//        http.csrf(c -> {
//            // customize csrf behaviour by override it self
//            c.csrfTokenRepository(customCsrfTokenRepository);
//        });

        http.addFilterAfter(customCsrfFilter, CsrfFilter.class);

    }
}

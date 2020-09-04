package io.tam.ssc1.controller;

import io.tam.ssc1.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final JdbcUserDetailsManager jdbcUserDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public HelloController(JdbcUserDetailsManager jdbcUserDetailsManager,
                           PasswordEncoder passwordEncoder) {
        this.jdbcUserDetailsManager = jdbcUserDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }

    @PostMapping("/user")
    public void addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        jdbcUserDetailsManager.createUser(user);
    }

}

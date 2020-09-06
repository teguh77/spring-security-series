package io.tam.ssc1.security.manager;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenManager {

    // hanya contoh, jangan digunakan pada aplikasi
    private final Set<String> tokens = new HashSet<>();

    public void add(String token) {
        tokens.add(token);
    }

    public boolean contains(String token) {
        return tokens.contains(token);
    }
}

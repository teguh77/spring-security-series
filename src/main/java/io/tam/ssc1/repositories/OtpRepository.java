package io.tam.ssc1.repositories;

import io.tam.ssc1.entities.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Integer> {

    Optional<Otp> findByOtp(String otp);
}

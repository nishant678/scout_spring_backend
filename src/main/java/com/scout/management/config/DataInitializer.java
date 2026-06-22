package com.scout.management.config;

import com.scout.management.entity.UserEntity;
import com.scout.management.enums.Role;
import com.scout.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        backfillUserIds();
        if (!userRepository.existsByEmail("admin@scout.com")) {
            userRepository.save(UserEntity.builder()
                    .userId("KS10000")
                    .name("Admin")
                    .email("admin@scout.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.SUPER_ADMIN)
                    .build());
        }
    }

    private void backfillUserIds() {
        var users = userRepository.findAll();
        SecureRandom random = new SecureRandom();
        for (var user : users) {
            if (user.getUserId() == null) {
                String id;
                do {
                    id = "KS" + (10000 + random.nextInt(90000));
                } while (userRepository.existsByUserId(id));
                user.setUserId(id);
                userRepository.save(user);
            }
        }
    }
}

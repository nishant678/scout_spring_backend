package com.scout.management.config;

import com.scout.management.entity.UserEntity;
import com.scout.management.enums.Role;
import com.scout.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByRole(Role.SUPER_ADMIN)) {
            userRepository.save(UserEntity.builder()
                    .name("Super Admin")
                    .email("admin@scout.com")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.SUPER_ADMIN)
                    .build());
        }
    }
}

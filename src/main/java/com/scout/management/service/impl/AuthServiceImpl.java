package com.scout.management.service.impl;

import com.scout.management.dto.request.LoginRequest;
import com.scout.management.dto.request.RegisterRequest;
import com.scout.management.dto.response.AuthResponse;
import com.scout.management.entity.UserEntity;
import com.scout.management.enums.Role;
import com.scout.management.exception.BadRequestException;
import com.scout.management.exception.DuplicateResourceException;
import com.scout.management.exception.UnauthorizedException;
import com.scout.management.repository.UserRepository;
import com.scout.management.security.JwtService;
import com.scout.management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(LoginRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }
        if (!user.isActive()) {
            throw new UnauthorizedException("Account is deactivated");
        }

        String token = jwtService.generateToken(user.getEmail());
        return AuthResponse.builder()
                .token(token).email(user.getEmail()).name(user.getName())
                .role(user.getRole().name()).id(user.getId()).build();
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }
        if (request.getPassword().length() < 6) {
            throw new BadRequestException("Password must be at least 6 characters");
        }

        var user = userRepository.save(UserEntity.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ADMIN)
                .isActive(true)
                .build());

        String token = jwtService.generateToken(user.getEmail());
        return AuthResponse.builder()
                .token(token).email(user.getEmail()).name(user.getName())
                .role(user.getRole().name()).id(user.getId()).build();
    }
}

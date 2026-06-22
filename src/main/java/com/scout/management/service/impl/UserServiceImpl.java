package com.scout.management.service.impl;

import com.scout.management.dto.request.CreateUserRequest;
import com.scout.management.dto.response.UserResponse;
import com.scout.management.entity.UserEntity;
import com.scout.management.enums.Role;
import com.scout.management.exception.BadRequestException;
import com.scout.management.exception.DuplicateResourceException;
import com.scout.management.exception.ResourceNotFoundException;
import com.scout.management.repository.UserRepository;
import com.scout.management.service.UserService;
import com.scout.management.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityUtil securityUtil;

    private String generateUserId() {
        SecureRandom random = new SecureRandom();
        int num = 10000 + random.nextInt(90000);
        return "KS" + num;
    }

    private String generatePassword() {
        SecureRandom random = new SecureRandom();
        String chars = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz23456789";
        StringBuilder pwd = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            pwd.append(chars.charAt(random.nextInt(chars.length())));
        }
        return pwd.toString();
    }

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }

        UserEntity currentUser = securityUtil.getCurrentUser();
        Role currentRole = currentUser.getRole();

        boolean isAdmin = currentRole == Role.SUPER_ADMIN || currentRole == Role.ADMIN;
        boolean isLeader = currentRole == Role.LEADER;

        if (isLeader && request.getRole() != Role.USER) {
            throw new BadRequestException("Scout Leader can only create User accounts");
        }
        if (!isAdmin && !isLeader) {
            throw new BadRequestException("User cannot create accounts");
        }

        String userId = generateUserId();
        while (userRepository.existsByUserId(userId)) {
            userId = generateUserId();
        }

        String rawPassword = generatePassword();

        UserEntity user = userRepository.save(UserEntity.builder()
                .userId(userId)
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(rawPassword))
                .role(request.getRole())
                .isActive(true)
                .createdBy(currentUser.getId())
                .build());

        UserResponse response = UserResponse.from(user);
        return UserResponse.builder()
                .id(response.getId())
                .userId(response.getUserId())
                .name(response.getName())
                .email(response.getEmail())
                .phone(response.getPhone())
                .role(response.getRole())
                .isActive(response.isActive())
                .createdAt(response.getCreatedAt())
                .password(rawPassword)
                .build();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        UserEntity currentUser = securityUtil.getCurrentUser();
        List<UserEntity> users;
        Role role = currentUser.getRole();
        boolean isAdmin = role == Role.SUPER_ADMIN || role == Role.ADMIN;
        if (isAdmin) {
            users = userRepository.findByRoleNot(Role.ADMIN);
        } else {
            users = userRepository.findByCreatedBy(currentUser.getId());
        }
        return users.stream().map(UserResponse::from).toList();
    }

    @Override
    public List<UserResponse> getUsersByRole(String role) {
        try {
            Role r = Role.valueOf(role.toUpperCase());
            return userRepository.findByRoleNot(Role.ADMIN).stream()
                    .filter(u -> u.getRole() == r)
                    .map(UserResponse::from)
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid role: " + role);
        }
    }



    @Override
    public UserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserResponse.from(user);
    }

    @Override
    public UserResponse toggleUserStatus(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setActive(!user.isActive());
        userRepository.save(user);
        return UserResponse.from(user);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}
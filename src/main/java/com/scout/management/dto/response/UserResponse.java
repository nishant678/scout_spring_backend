package com.scout.management.dto.response;

import com.scout.management.entity.UserEntity;
import com.scout.management.enums.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String userId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private Role role;
    private boolean isActive;
    private LocalDateTime createdAt;

    public static UserResponse from(UserEntity entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .role(entity.getRole())
                .isActive(entity.isActive())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
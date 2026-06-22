package com.scout.management.service;

import com.scout.management.dto.request.CreateUserRequest;
import com.scout.management.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest request);
    List<UserResponse> getAllUsers();
    List<UserResponse> getUsersByRole(String role);
    UserResponse getUserById(Long id);
    UserResponse toggleUserStatus(Long id);
    void deleteUser(Long id);
}
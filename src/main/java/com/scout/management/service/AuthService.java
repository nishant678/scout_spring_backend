package com.scout.management.service;

import com.scout.management.dto.request.LoginRequest;
import com.scout.management.dto.request.RegisterRequest;
import com.scout.management.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
    AuthResponse loginByUserId(String userId, String password);
}

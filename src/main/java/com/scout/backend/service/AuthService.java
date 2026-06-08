package com.scout.backend.service;
import com.hr.demo.reaponse.AuthResponse;
import com.hr.demo.dto.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
//    AuthResponse register(CreateUserRequest request);
}

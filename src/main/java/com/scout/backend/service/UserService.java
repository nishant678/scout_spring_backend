package com.scout.backend.service;

import com.hr.demo.dto.AddCompanyUserRequest;
import com.hr.demo.reaponse.UserResponse;

public interface UserService {
    UserResponse addCompanyUser(AddCompanyUserRequest request);
}

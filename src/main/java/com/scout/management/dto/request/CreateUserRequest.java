package com.scout.management.dto.request;

import com.scout.management.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    @NotBlank
    private String name;
    @NotBlank @Email
    private String email;
    private String phone;
    @NotNull
    private Role role;
}
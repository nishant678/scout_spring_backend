package com.scout.management.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    @NotBlank
    private String name;
    @NotBlank @Email
    private String email;
    private String phone;
    @NotBlank
    private String section;
    @NotBlank
    private String unit;
    @NotBlank
    private String county;
    private String notes;
}

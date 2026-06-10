package com.scout.management.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberRequest {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank @Email
    private String email;
    private String phone;
    @NotBlank
    private String section;
    @NotBlank
    private String unit;
    @NotBlank
    private String county;
    private LocalDate dateOfBirth;
    private String address;
}

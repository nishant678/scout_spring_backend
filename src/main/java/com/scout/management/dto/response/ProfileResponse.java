package com.scout.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProfileResponse {
    private String name;
    private String email;
    private String phone;
    private String role;
}

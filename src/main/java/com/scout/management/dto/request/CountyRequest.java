package com.scout.management.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountyRequest {
    @NotBlank(message = "County name is required")
    private String name;
    @NotBlank(message = "County code is required")
    private String code;
}
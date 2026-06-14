package com.scout.management.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionRequest {
    @NotBlank(message = "Section name is required")
    private String name;
    private String description;
}
package com.scout.management.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnitRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String code;
    @NotBlank
    private String county;
    private String coordinator;
    private String address;
}

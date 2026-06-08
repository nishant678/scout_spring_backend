package com.scout.backend.reaponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleResponse {
    private Long id;
    private String name;
    private String description;
    private Long companyId;
}
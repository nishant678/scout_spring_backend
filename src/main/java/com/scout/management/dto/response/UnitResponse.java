package com.scout.management.dto.response;

import com.scout.management.entity.UnitEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UnitResponse {
    private Long id;
    private String name;
    private String code;
    private String county;
    private String coordinator;
    private String address;
    private boolean isActive;
    private LocalDateTime createdAt;

    public static UnitResponse from(UnitEntity e) {
        return UnitResponse.builder()
                .id(e.getId()).name(e.getName()).code(e.getCode()).county(e.getCounty())
                .coordinator(e.getCoordinator()).address(e.getAddress())
                .isActive(e.isActive()).createdAt(e.getCreatedAt()).build();
    }
}

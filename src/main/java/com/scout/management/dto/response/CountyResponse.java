package com.scout.management.dto.response;

import com.scout.management.entity.CountyEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CountyResponse {
    private Long id;
    private String name;
    private String code;
    private boolean isActive;
    private LocalDateTime createdAt;

    public static CountyResponse from(CountyEntity e) {
        return CountyResponse.builder()
                .id(e.getId()).name(e.getName()).code(e.getCode())
                .isActive(e.isActive()).createdAt(e.getCreatedAt()).build();
    }
}
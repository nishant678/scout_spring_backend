package com.scout.management.dto.response;

import com.scout.management.entity.SectionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SectionResponse {
    private Long id;
    private String name;
    private String description;
    private boolean isActive;
    private LocalDateTime createdAt;

    public static SectionResponse from(SectionEntity e) {
        return SectionResponse.builder()
                .id(e.getId()).name(e.getName()).description(e.getDescription())
                .isActive(e.isActive()).createdAt(e.getCreatedAt()).build();
    }
}
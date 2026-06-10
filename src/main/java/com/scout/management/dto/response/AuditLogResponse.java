package com.scout.management.dto.response;

import com.scout.management.entity.AuditLogEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class AuditLogResponse {
    private Long id;
    private String action;
    private String entityType;
    private Long entityId;
    private String userName;
    private String details;
    private LocalDateTime createdAt;

    public static AuditLogResponse from(AuditLogEntity e) {
        return AuditLogResponse.builder()
                .id(e.getId()).action(e.getAction()).entityType(e.getEntityType())
                .entityId(e.getEntityId())
                .userName(e.getUser() != null ? e.getUser().getName() : "System")
                .details(e.getDetails()).createdAt(e.getCreatedAt()).build();
    }
}

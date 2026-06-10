package com.scout.management.dto.response;

import com.scout.management.entity.ApprovalEntity;
import com.scout.management.enums.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ApprovalResponse {
    private Long id;
    private Long registrationId;
    private String approvedBy;
    private RegistrationStatus status;
    private String comment;
    private LocalDateTime createdAt;

    public static ApprovalResponse from(ApprovalEntity e) {
        return ApprovalResponse.builder()
                .id(e.getId()).registrationId(e.getRegistration().getId())
                .approvedBy(e.getApprovedBy().getName()).status(e.getStatus())
                .comment(e.getComment()).createdAt(e.getCreatedAt()).build();
    }
}

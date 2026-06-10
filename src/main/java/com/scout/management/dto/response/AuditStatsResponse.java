package com.scout.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder @AllArgsConstructor
public class AuditStatsResponse {
    private long totalActivities;
    private double successRate;
    private long securityAlerts;
}

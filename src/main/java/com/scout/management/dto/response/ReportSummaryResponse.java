package com.scout.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReportSummaryResponse {
    private long totalMembers;
    private long newRegistrations;
    private long pendingApprovals;
    private long totalRevenue;
    private long activeCounties;
    private long totalCounties;
    private long totalRevenueChange;
}

package com.scout.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class DashboardMetricsResponse {
    private MetricValue totalMembers;
    private MetricValue pendingApprovals;
    private MetricValue totalUnits;
    private MetricValue totalRevenue;
    private ActiveCountyCount activeCounties;
    private long newRegistrations;
    private List<ChartDataPoint> registrationsOverview;
    private List<ChartDataPoint> membersBySection;
    private List<ChartDataPoint> revenueBreakdown;

    @Getter @Builder @AllArgsConstructor
    public static class MetricValue {
        private long current;
        private long change;
        private String trend;
    }

    @Getter @Builder @AllArgsConstructor
    public static class ActiveCountyCount {
        private int current;
        private int total;
    }

    @Getter @Builder @AllArgsConstructor
    public static class ChartDataPoint {
        private String label;
        private Number value;
    }
}

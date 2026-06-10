package com.scout.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Builder @AllArgsConstructor
public class FinanceSummaryResponse {
    private BigDecimal totalRevenue;
    private BigDecimal membershipFees;
    private BigDecimal totalExpenses;
    private BigDecimal availableBalance;
    private double revenueGrowth;
    private double expenseChange;
    private List<DashboardMetricsResponse.ChartDataPoint> revenueBreakdown;
}

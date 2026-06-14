package com.scout.management.service;

import com.scout.management.dto.response.DashboardMetricsResponse.ChartDataPoint;
import com.scout.management.dto.response.ReportSummaryResponse;

import java.util.List;

public interface ReportsService {
    List<ChartDataPoint> getRegistrationTrends(String period);
    List<ChartDataPoint> getMembershipBySection(String type);
    ReportSummaryResponse getSummary(String period);
    List<ChartDataPoint> getCountyBreakdown();
}

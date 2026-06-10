package com.scout.management.service;

import com.scout.management.dto.response.DashboardMetricsResponse.ChartDataPoint;

import java.util.List;

public interface ReportsService {
    List<ChartDataPoint> getRegistrationTrends(String period);
    List<ChartDataPoint> getMembershipBySection(String type);
}

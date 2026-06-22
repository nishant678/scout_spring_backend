package com.scout.management.controller;

import com.scout.management.dto.response.ApiResponse;
import com.scout.management.dto.response.DashboardMetricsResponse;
import com.scout.management.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Aggregated dashboard metrics and KPIs")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/metrics")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LEADER')")
    @Operation(summary = "Get dashboard metrics", description = "Returns key metrics: total members, pending approvals, units, revenue, and chart data")
    public ResponseEntity<ApiResponse<DashboardMetricsResponse>> getMetrics() {
        return ResponseEntity.ok(ApiResponse.success("Dashboard metrics retrieved", dashboardService.getMetrics()));
    }
}

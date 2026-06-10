package com.scout.management.controller;

import com.scout.management.dto.response.ApiResponse;
import com.scout.management.dto.response.DashboardMetricsResponse.ChartDataPoint;
import com.scout.management.service.ReportsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reports", description = "Analytics and chart data for reports")
public class ReportsController {

    private final ReportsService reportsService;

    @GetMapping("/registration-trends")
    @Operation(summary = "Get registration trends", description = "Returns chart data points for registration trends over a period (e.g. 30d, 6m, 1y)")
    public ResponseEntity<ApiResponse<List<ChartDataPoint>>> getRegistrationTrends(
            @RequestParam(defaultValue = "30d") String period) {
        return ResponseEntity.ok(ApiResponse.success("Registration trends retrieved",
                reportsService.getRegistrationTrends(period)));
    }

    @GetMapping("/membership-by-section")
    @Operation(summary = "Get membership by section", description = "Returns membership distribution across scout sections")
    public ResponseEntity<ApiResponse<List<ChartDataPoint>>> getMembershipBySection(
            @RequestParam(defaultValue = "current") String type) {
        return ResponseEntity.ok(ApiResponse.success("Membership by section retrieved",
                reportsService.getMembershipBySection(type)));
    }
}

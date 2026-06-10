package com.scout.management.controller;

import com.scout.management.dto.response.*;
import com.scout.management.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Statistics", description = "Aggregated statistics for members, units, and audit logs")
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/members/stats")
    @Operation(summary = "Get member statistics", description = "Returns total members, active count, and pending renewals")
    public ResponseEntity<ApiResponse<MemberStatsResponse>> getMemberStats() {
        return ResponseEntity.ok(ApiResponse.success("Member stats retrieved", statsService.getMemberStats()));
    }

    @GetMapping("/units/stats")
    @Operation(summary = "Get unit statistics", description = "Returns total units, counties, average size, and top counties")
    public ResponseEntity<ApiResponse<UnitStatsResponse>> getUnitStats() {
        return ResponseEntity.ok(ApiResponse.success("Unit stats retrieved", statsService.getUnitStats()));
    }

    @GetMapping("/audit-logs/stats")
    @Operation(summary = "Get audit log statistics", description = "Returns total activities, success rate, and security alerts")
    public ResponseEntity<ApiResponse<AuditStatsResponse>> getAuditStats() {
        return ResponseEntity.ok(ApiResponse.success("Audit stats retrieved", statsService.getAuditStats()));
    }
}

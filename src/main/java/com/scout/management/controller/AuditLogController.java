package com.scout.management.controller;

import com.scout.management.dto.response.ApiResponse;
import com.scout.management.dto.response.AuditLogResponse;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
@Tag(name = "Audit Logs", description = "View system audit trail and activity logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    @Operation(summary = "List audit logs", description = "Returns paginated audit log entries sorted by newest first")
    public ResponseEntity<ApiResponse<PagedResponse<AuditLogResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success("Audit logs retrieved", auditLogService.getAll(page, size)));
    }
}

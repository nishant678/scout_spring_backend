package com.scout.management.controller;

import com.scout.management.dto.request.ApprovalRequest;
import com.scout.management.dto.response.ApiResponse;
import com.scout.management.dto.response.ApprovalResponse;
import com.scout.management.service.ApprovalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approvals")
@RequiredArgsConstructor
@Tag(name = "Approvals", description = "Review and approve/reject registration requests")
public class ApprovalController {

    private final ApprovalService approvalService;

    @PostMapping("/{registrationId}/approve")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LEADER')")
    @Operation(summary = "Approve a registration", description = "Marks a pending registration as approved")
    public ResponseEntity<ApiResponse<ApprovalResponse>> approve(
            @PathVariable Long registrationId, @Valid @RequestBody ApprovalRequest request) {
        request.setRegistrationId(registrationId);
        return ResponseEntity.ok(ApiResponse.success("Registration approved",
                approvalService.approve(registrationId, request)));
    }

    @PostMapping("/{registrationId}/reject")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LEADER')")
    @Operation(summary = "Reject a registration", description = "Marks a pending registration as rejected")
    public ResponseEntity<ApiResponse<ApprovalResponse>> reject(
            @PathVariable Long registrationId, @Valid @RequestBody ApprovalRequest request) {
        request.setRegistrationId(registrationId);
        return ResponseEntity.ok(ApiResponse.success("Registration rejected",
                approvalService.reject(registrationId, request)));
    }

    @GetMapping("/{registrationId}/history")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LEADER')")
    @Operation(summary = "Get approval history", description = "Returns all approval actions for a registration")
    public ResponseEntity<ApiResponse<List<ApprovalResponse>>> getHistory(@PathVariable Long registrationId) {
        return ResponseEntity.ok(ApiResponse.success("Approval history retrieved",
                approvalService.getHistory(registrationId)));
    }
}

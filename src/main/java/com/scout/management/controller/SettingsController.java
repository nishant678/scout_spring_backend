package com.scout.management.controller;

import com.scout.management.dto.response.ApiResponse;
import com.scout.management.dto.response.ProfileResponse;
import com.scout.management.entity.UserEntity;
import com.scout.management.repository.UserRepository;
import com.scout.management.service.AuditLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
@Tag(name = "Settings", description = "User profile and system administration settings")
public class SettingsController {

    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    @GetMapping("/profile")
    @Operation(summary = "Get profile", description = "Returns the current authenticated user's profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile(Authentication auth) {
        UserEntity user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(ApiResponse.success("Profile retrieved", ProfileResponse.builder()
                .name(user.getName()).email(user.getEmail()).phone("").role(user.getRole().name()).build()));
    }

    @PatchMapping("/profile")
    @Operation(summary = "Update profile", description = "Updates the current user's name and/or email")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(Authentication auth,
                                                                       @RequestBody Map<String, String> body) {
        UserEntity user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (body.containsKey("name")) user.setName(body.get("name"));
        if (body.containsKey("email")) user.setEmail(body.get("email"));
        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.success("Profile updated", ProfileResponse.builder()
                .name(user.getName()).email(user.getEmail()).phone("").role(user.getRole().name()).build()));
    }

    @PostMapping("/purge-audit-logs")
    @Operation(summary = "Purge old audit logs", description = "Deletes audit log entries older than 3 months")
    public ResponseEntity<ApiResponse<Void>> purgeAuditLogs() {
        auditLogService.purgeLogsOlderThan(LocalDateTime.now().minusMonths(3));
        return ResponseEntity.ok(ApiResponse.success("Old audit logs purged"));
    }

    @PostMapping("/reset-cache")
    @Operation(summary = "Reset system cache", description = "Initiates a cache reset (placeholder)")
    public ResponseEntity<ApiResponse<Void>> resetCache() {
        return ResponseEntity.ok(ApiResponse.success("Cache reset initiated"));
    }
}

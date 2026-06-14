package com.scout.management.controller;

import com.scout.management.dto.request.ChangePasswordRequest;
import com.scout.management.dto.response.ApiResponse;
import com.scout.management.dto.response.NotificationResponse;
import com.scout.management.dto.response.ProfileResponse;
import com.scout.management.dto.response.RegionalSettingsResponse;
import com.scout.management.service.SettingsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
@Tag(name = "Settings", description = "User profile, notifications, security, regional and system administration settings")
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping("/profile")
    @Operation(summary = "Get profile", description = "Returns the current authenticated user's profile")
    public ResponseEntity<ApiResponse<ProfileResponse>> getProfile() {
        return ResponseEntity.ok(ApiResponse.success("Profile retrieved", settingsService.getProfile()));
    }

    @PatchMapping("/profile")
    @Operation(summary = "Update profile", description = "Updates the current user's name, email and/or phone")
    public ResponseEntity<ApiResponse<ProfileResponse>> updateProfile(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(ApiResponse.success("Profile updated", settingsService.updateProfile(body)));
    }

    @GetMapping("/notifications")
    @Operation(summary = "Get notification preferences")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotifications() {
        return ResponseEntity.ok(ApiResponse.success("Notification preferences retrieved", settingsService.getNotifications()));
    }

    @PatchMapping("/notifications")
    @Operation(summary = "Update notification preferences")
    public ResponseEntity<ApiResponse<NotificationResponse>> updateNotifications(@RequestBody Map<String, Boolean> body) {
        return ResponseEntity.ok(ApiResponse.success("Notification preferences updated", settingsService.updateNotifications(body)));
    }

    @PutMapping("/security/password")
    @Operation(summary = "Change password")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        settingsService.changePassword(request);
        return ResponseEntity.ok(ApiResponse.success("Password changed successfully"));
    }

    @GetMapping("/regional")
    @Operation(summary = "Get regional settings")
    public ResponseEntity<ApiResponse<RegionalSettingsResponse>> getRegionalSettings() {
        return ResponseEntity.ok(ApiResponse.success("Regional settings retrieved", settingsService.getRegionalSettings()));
    }

    @PatchMapping("/regional")
    @Operation(summary = "Update regional settings")
    public ResponseEntity<ApiResponse<RegionalSettingsResponse>> updateRegionalSettings(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(ApiResponse.success("Regional settings updated", settingsService.updateRegionalSettings(body)));
    }

    @PostMapping("/purge-audit-logs")
    @Operation(summary = "Purge old audit logs", description = "Deletes audit log entries older than 3 months")
    public ResponseEntity<ApiResponse<Void>> purgeAuditLogs() {
        settingsService.purgeAuditLogs();
        return ResponseEntity.ok(ApiResponse.success("Old audit logs purged"));
    }

    @PostMapping("/reset-cache")
    @Operation(summary = "Reset system cache")
    public ResponseEntity<ApiResponse<Void>> resetCache() {
        settingsService.resetCache();
        return ResponseEntity.ok(ApiResponse.success("Cache reset initiated"));
    }
}

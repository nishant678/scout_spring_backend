package com.scout.management.service;

import com.scout.management.dto.request.ChangePasswordRequest;
import com.scout.management.dto.response.NotificationResponse;
import com.scout.management.dto.response.ProfileResponse;
import com.scout.management.dto.response.RegionalSettingsResponse;

import java.util.Map;

public interface SettingsService {
    ProfileResponse getProfile();
    ProfileResponse updateProfile(Map<String, String> body);
    NotificationResponse getNotifications();
    NotificationResponse updateNotifications(Map<String, Boolean> body);
    void changePassword(ChangePasswordRequest request);
    RegionalSettingsResponse getRegionalSettings();
    RegionalSettingsResponse updateRegionalSettings(Map<String, String> body);
    void purgeAuditLogs();
    void resetCache();
}
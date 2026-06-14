package com.scout.management.service.impl;

import com.scout.management.dto.request.ChangePasswordRequest;
import com.scout.management.dto.response.NotificationResponse;
import com.scout.management.dto.response.ProfileResponse;
import com.scout.management.dto.response.RegionalSettingsResponse;
import com.scout.management.entity.UserEntity;
import com.scout.management.exception.BadRequestException;
import com.scout.management.repository.UserRepository;
import com.scout.management.service.AuditLogService;
import com.scout.management.service.SettingsService;
import com.scout.management.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {

    private final SecurityUtil securityUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuditLogService auditLogService;

    @Override
    public ProfileResponse getProfile() {
        UserEntity user = securityUtil.getCurrentUser();
        return toProfileResponse(user);
    }

    @Override
    @Transactional
    public ProfileResponse updateProfile(Map<String, String> body) {
        UserEntity user = securityUtil.getCurrentUser();
        if (body.containsKey("name")) user.setName(body.get("name"));
        if (body.containsKey("email")) user.setEmail(body.get("email"));
        if (body.containsKey("phone")) user.setPhone(body.get("phone"));
        userRepository.save(user);
        return toProfileResponse(user);
    }

    @Override
    public NotificationResponse getNotifications() {
        UserEntity user = securityUtil.getCurrentUser();
        return NotificationResponse.builder()
                .email(Boolean.TRUE.equals(user.getNotificationEmail()))
                .sms(Boolean.TRUE.equals(user.getNotificationSms()))
                .security(Boolean.TRUE.equals(user.getNotificationSecurity()))
                .build();
    }

    @Override
    @Transactional
    public NotificationResponse updateNotifications(Map<String, Boolean> body) {
        UserEntity user = securityUtil.getCurrentUser();
        if (body.containsKey("email")) user.setNotificationEmail(body.get("email"));
        if (body.containsKey("sms")) user.setNotificationSms(body.get("sms"));
        if (body.containsKey("security")) user.setNotificationSecurity(body.get("security"));
        userRepository.save(user);
        return NotificationResponse.builder()
                .email(Boolean.TRUE.equals(user.getNotificationEmail()))
                .sms(Boolean.TRUE.equals(user.getNotificationSms()))
                .security(Boolean.TRUE.equals(user.getNotificationSecurity()))
                .build();
    }

    @Override
    public RegionalSettingsResponse getRegionalSettings() {
        UserEntity user = securityUtil.getCurrentUser();
        return RegionalSettingsResponse.builder()
                .timezone(user.getTimezone() != null ? user.getTimezone() : "Africa/Nairobi")
                .currency(user.getCurrency() != null ? user.getCurrency() : "KES")
                .dateFormat(user.getDateFormat() != null ? user.getDateFormat() : "MMM d, yyyy")
                .build();
    }

    @Override
    @Transactional
    public RegionalSettingsResponse updateRegionalSettings(Map<String, String> body) {
        UserEntity user = securityUtil.getCurrentUser();
        if (body.containsKey("timezone")) user.setTimezone(body.get("timezone"));
        if (body.containsKey("currency")) user.setCurrency(body.get("currency"));
        if (body.containsKey("dateFormat")) user.setDateFormat(body.get("dateFormat"));
        userRepository.save(user);
        return RegionalSettingsResponse.builder()
                .timezone(user.getTimezone() != null ? user.getTimezone() : "Africa/Nairobi")
                .currency(user.getCurrency() != null ? user.getCurrency() : "KES")
                .dateFormat(user.getDateFormat() != null ? user.getDateFormat() : "MMM d, yyyy")
                .build();
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        UserEntity user = securityUtil.getCurrentUser();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void purgeAuditLogs() {
        auditLogService.purgeLogsOlderThan(LocalDateTime.now().minusMonths(3));
    }

    @Override
    @Transactional
    public void resetCache() {
    }

    private ProfileResponse toProfileResponse(UserEntity user) {
        return ProfileResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone() != null ? user.getPhone() : "")
                .role(user.getRole().name())
                .build();
    }
}
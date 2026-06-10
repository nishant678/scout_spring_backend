package com.scout.management.service;

import com.scout.management.dto.response.AuditLogResponse;
import com.scout.management.dto.response.PagedResponse;

import java.time.LocalDateTime;

public interface AuditLogService {
    PagedResponse<AuditLogResponse> getAll(int page, int size);
    void log(String action, String entityType, Long entityId, Long userId, String details);
    void purgeLogsOlderThan(LocalDateTime date);
}

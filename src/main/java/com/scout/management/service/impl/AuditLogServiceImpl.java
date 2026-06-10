package com.scout.management.service.impl;

import com.scout.management.dto.response.AuditLogResponse;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.entity.AuditLogEntity;
import com.scout.management.entity.UserEntity;
import com.scout.management.repository.AuditLogRepository;
import com.scout.management.repository.UserRepository;
import com.scout.management.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    @Override
    public PagedResponse<AuditLogResponse> getAll(int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        var result = auditLogRepository.findAll(pageable);
        var content = result.getContent().stream().map(AuditLogResponse::from).toList();
        return PagedResponse.from(result, content);
    }

    @Override
    public void log(String action, String entityType, Long entityId, Long userId, String details) {
        UserEntity user = userId != null ? userRepository.findById(userId).orElse(null) : null;
        auditLogRepository.save(AuditLogEntity.builder()
                .action(action).entityType(entityType).entityId(entityId)
                .user(user).details(details).build());
    }

    @Override
    @Transactional
    public void purgeLogsOlderThan(LocalDateTime date) {
        auditLogRepository.deleteByCreatedAtBefore(date);
    }
}

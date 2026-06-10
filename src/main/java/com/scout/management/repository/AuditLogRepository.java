package com.scout.management.repository;

import com.scout.management.entity.AuditLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, Long> {
    Page<AuditLogEntity> findByAction(String action, Pageable pageable);
    Page<AuditLogEntity> findByEntityType(String entityType, Pageable pageable);
    long countByAction(String action);
    @Transactional
    void deleteByCreatedAtBefore(LocalDateTime date);
}

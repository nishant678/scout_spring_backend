package com.scout.management.repository;

import com.scout.management.entity.ApprovalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApprovalRepository extends JpaRepository<ApprovalEntity, Long> {
    List<ApprovalEntity> findByRegistrationIdOrderByCreatedAtDesc(Long registrationId);

    @Query("SELECT COUNT(a) FROM ApprovalEntity a WHERE a.status = 'PENDING'")
    long countPending();
}

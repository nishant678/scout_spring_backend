package com.scout.management.repository;

import com.scout.management.entity.RegistrationEntity;
import com.scout.management.enums.RegistrationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity, Long> {
    long countByStatus(RegistrationStatus status);

    @Query("SELECT COUNT(r) FROM RegistrationEntity r WHERE r.submissionDate >= :since")
    long countSince(LocalDateTime since);

    @Query(value = "SELECT CAST(r.submission_date AS date), COUNT(r) FROM registrations r " +
           "WHERE r.submission_date >= :since GROUP BY CAST(r.submission_date AS date) ORDER BY CAST(r.submission_date AS date)", nativeQuery = true)
    List<Object[]> dailyRegistrationsSince(LocalDateTime since);

    Page<RegistrationEntity> findByStatus(RegistrationStatus status, Pageable pageable);
    Page<RegistrationEntity> findByCounty(String county, Pageable pageable);
}

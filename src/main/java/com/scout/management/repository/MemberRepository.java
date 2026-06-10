package com.scout.management.repository;

import com.scout.management.entity.MemberEntity;
import com.scout.management.enums.MemberStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    long countByStatus(MemberStatus status);

    @Query("SELECT COUNT(m) FROM MemberEntity m WHERE m.status = 'ACTIVE'")
    long countActiveMembers();

    @Query("SELECT m.county, COUNT(m) FROM MemberEntity m GROUP BY m.county")
    List<Object[]> countByCounty();

    @Query("SELECT m.section, COUNT(m) FROM MemberEntity m GROUP BY m.section")
    List<Object[]> countBySection();

    @Query("SELECT COUNT(m) FROM MemberEntity m WHERE m.createdAt >= :since")
    long countSince(java.time.LocalDateTime since);

    Page<MemberEntity> findByStatus(MemberStatus status, Pageable pageable);
    Page<MemberEntity> findByCounty(String county, Pageable pageable);
    Page<MemberEntity> findBySection(String section, Pageable pageable);
}

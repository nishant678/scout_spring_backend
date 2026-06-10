package com.scout.management.repository;

import com.scout.management.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UnitRepository extends JpaRepository<UnitEntity, Long> {
    Optional<UnitEntity> findByCode(String code);
    boolean existsByCode(String code);
    long countByIsActiveTrue();
    long countByCounty(String county);

    @Query("SELECT u.county, COUNT(u) FROM UnitEntity u GROUP BY u.county")
    List<Object[]> countByCounty();
}

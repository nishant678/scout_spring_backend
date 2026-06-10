package com.scout.management.repository;

import com.scout.management.entity.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SectionRepository extends JpaRepository<SectionEntity, Long> {
    Optional<SectionEntity> findByName(String name);
    boolean existsByName(String name);
}

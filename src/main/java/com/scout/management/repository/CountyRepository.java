package com.scout.management.repository;

import com.scout.management.entity.CountyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountyRepository extends JpaRepository<CountyEntity, Long> {
    Optional<CountyEntity> findByName(String name);
    boolean existsByName(String name);
}

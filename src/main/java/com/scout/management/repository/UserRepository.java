package com.scout.management.repository;

import com.scout.management.entity.UserEntity;
import com.scout.management.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);
    boolean existsByRole(Role role);
    List<UserEntity> findByRoleNot(Role role);
    List<UserEntity> findByCreatedBy(Long createdBy);
}

package com.scout.management.repository;

import com.scout.management.entity.FinanceTransactionEntity;
import com.scout.management.enums.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface FinanceTransactionRepository extends JpaRepository<FinanceTransactionEntity, Long> {

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FinanceTransactionEntity f WHERE f.type = :type")
    BigDecimal totalByType(TransactionType type);

    @Query("SELECT COALESCE(SUM(f.amount), 0) FROM FinanceTransactionEntity f " +
           "WHERE f.type = :type AND f.createdAt >= :since")
    BigDecimal totalByTypeSince(TransactionType type, LocalDateTime since);

    @Query("SELECT f.category, COALESCE(SUM(f.amount), 0) FROM FinanceTransactionEntity f " +
           "WHERE f.type = 'INCOME' GROUP BY f.category")
    List<Object[]> incomeByCategory();

    Page<FinanceTransactionEntity> findByType(TransactionType type, Pageable pageable);
}

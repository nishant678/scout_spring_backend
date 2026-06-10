package com.scout.management.service.impl;

import com.scout.management.dto.request.TransactionRequest;
import com.scout.management.dto.response.DashboardMetricsResponse.ChartDataPoint;
import com.scout.management.dto.response.FinanceResponse;
import com.scout.management.dto.response.FinanceSummaryResponse;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.entity.FinanceTransactionEntity;
import com.scout.management.enums.TransactionType;
import com.scout.management.repository.FinanceTransactionRepository;
import com.scout.management.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinanceServiceImpl implements FinanceService {

    private final FinanceTransactionRepository financeRepository;

    @Override
    public PagedResponse<FinanceResponse> getAll(int page, int size, TransactionType type) {
        var pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        var result = type != null
                ? financeRepository.findByType(type, pageable)
                : financeRepository.findAll(pageable);
        return PagedResponse.from(result, result.getContent().stream().map(FinanceResponse::from).toList());
    }

    @Override
    public FinanceResponse getById(Long id) {
        return FinanceResponse.from(financeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found with id: " + id)));
    }

    @Override
    public FinanceResponse create(TransactionRequest request) {
        var entity = financeRepository.save(FinanceTransactionEntity.builder()
                .type(TransactionType.valueOf(request.getType()))
                .amount(request.getAmount())
                .description(request.getDescription())
                .category(request.getCategory())
                .referenceNumber("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .build());
        return FinanceResponse.from(entity);
    }

    @Override
    public FinanceSummaryResponse getSummary() {
        BigDecimal totalRevenue = financeRepository.totalByType(TransactionType.INCOME);
        BigDecimal totalExpenses = financeRepository.totalByType(TransactionType.EXPENSE);
        BigDecimal availableBalance = totalRevenue.subtract(totalExpenses);
        BigDecimal revenueLastYear = financeRepository.totalByTypeSince(TransactionType.INCOME,
                LocalDateTime.now().minusYears(1));
        BigDecimal expensesLastMonth = financeRepository.totalByTypeSince(TransactionType.EXPENSE,
                LocalDateTime.now().minusMonths(1));

        double revenueGrowth = revenueLastYear.compareTo(BigDecimal.ZERO) > 0
                ? totalRevenue.subtract(revenueLastYear).divide(revenueLastYear, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)).doubleValue()
                : 0;

        double expenseChange = totalExpenses.compareTo(BigDecimal.ZERO) > 0
                ? expensesLastMonth.subtract(totalExpenses).divide(totalExpenses, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)).doubleValue()
                : 0;

        var breakdown = financeRepository.incomeByCategory().stream()
                .map(r -> new ChartDataPoint(r[0] != null ? r[0].toString() : "Uncategorized", (Number) r[1]))
                .collect(Collectors.toList());

        return FinanceSummaryResponse.builder()
                .totalRevenue(totalRevenue)
                .membershipFees(totalRevenue.multiply(BigDecimal.valueOf(0.485)))
                .totalExpenses(totalExpenses)
                .availableBalance(availableBalance)
                .revenueGrowth(revenueGrowth)
                .expenseChange(expenseChange)
                .revenueBreakdown(breakdown)
                .build();
    }
}

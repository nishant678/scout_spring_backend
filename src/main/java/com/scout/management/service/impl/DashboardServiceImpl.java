package com.scout.management.service.impl;

import com.scout.management.dto.response.DashboardMetricsResponse;
import com.scout.management.dto.response.DashboardMetricsResponse.ChartDataPoint;
import com.scout.management.enums.RegistrationStatus;
import com.scout.management.enums.TransactionType;
import com.scout.management.repository.*;
import com.scout.management.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final MemberRepository memberRepository;
    private final RegistrationRepository registrationRepository;
    private final UnitRepository unitRepository;
    private final FinanceTransactionRepository financeRepository;
    private final CountyRepository countyRepository;

    @Override
    public DashboardMetricsResponse getMetrics() {
        long totalMembers = safeCall(() -> memberRepository.count(), 0L);
        long totalMembersLastWeek = safeCall(() -> memberRepository.countSince(LocalDateTime.now().minusDays(7)), 0L);

        long pendingApprovals = safeCall(() -> registrationRepository.countByStatus(RegistrationStatus.PENDING), 0L);
        long totalUnits = safeCall(() -> unitRepository.countByIsActiveTrue(), 0L);
        BigDecimal totalRevenue = safeCall(() -> financeRepository.totalByType(TransactionType.INCOME), BigDecimal.ZERO);
        BigDecimal revenueLastMonth = safeCall(() -> financeRepository.totalByTypeSince(TransactionType.INCOME, LocalDateTime.now().minusDays(30)), BigDecimal.ZERO);

        long newRegistrations = safeCall(() -> registrationRepository.countSince(LocalDateTime.now().minusDays(30)), 0L);
        long activeCountiesCount = safeCall(() -> countDistinctCounties(), 0L);
        long totalCounties = safeCall(() -> countyRepository.count(), 0L);

        return DashboardMetricsResponse.builder()
                .totalMembers(new DashboardMetricsResponse.MetricValue(totalMembers, totalMembersLastWeek, "up"))
                .pendingApprovals(new DashboardMetricsResponse.MetricValue(pendingApprovals, 0, pendingApprovals > 0 ? "up" : "down"))
                .totalUnits(new DashboardMetricsResponse.MetricValue(totalUnits, 0, "up"))
                .totalRevenue(new DashboardMetricsResponse.MetricValue(
                        nonNull(totalRevenue).longValue(), nonNull(revenueLastMonth).longValue(), "up"))
                .newRegistrations(newRegistrations)
                .activeCounties(new DashboardMetricsResponse.ActiveCountyCount(
                        (int) activeCountiesCount, (int) Math.max(totalCounties, activeCountiesCount)))
                .registrationsOverview(getRegistrationsOverview())
                .membersBySection(getMembersBySection())
                .revenueBreakdown(getRevenueBreakdown())
                .build();
    }

    private List<ChartDataPoint> getRegistrationsOverview() {
        try {
            return registrationRepository.dailyRegistrationsSince(LocalDateTime.now().minusDays(7))
                    .stream().map(r -> new ChartDataPoint(safeToString(r[0]), (Number) r[1]))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Failed to load registration overview: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<ChartDataPoint> getMembersBySection() {
        try {
            return memberRepository.countBySection()
                    .stream().map(r -> new ChartDataPoint(safeToString(r[0]), (Number) r[1]))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Failed to load members by section: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private List<ChartDataPoint> getRevenueBreakdown() {
        try {
            return financeRepository.incomeByCategory()
                    .stream().map(r -> new ChartDataPoint(safeToString(r[0]), (Number) r[1]))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Failed to load revenue breakdown: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private long countDistinctCounties() {
        return memberRepository.countByCounty().size();
    }

    private static String safeToString(Object obj) {
        return obj != null ? obj.toString() : "";
    }

    private static BigDecimal nonNull(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }

    @FunctionalInterface
    private interface SafeSupplier<T> {
        T get();
    }

    private static <T> T safeCall(SafeSupplier<T> supplier, T fallback) {
        try {
            return supplier.get();
        } catch (Exception e) {
            log.warn("Dashboard query failed: {}", e.getMessage());
            return fallback;
        }
    }
}

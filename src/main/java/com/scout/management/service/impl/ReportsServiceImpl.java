package com.scout.management.service.impl;

import com.scout.management.dto.response.DashboardMetricsResponse.ChartDataPoint;
import com.scout.management.dto.response.ReportSummaryResponse;
import com.scout.management.enums.RegistrationStatus;
import com.scout.management.enums.TransactionType;
import com.scout.management.repository.*;
import com.scout.management.service.ReportsService;
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
public class ReportsServiceImpl implements ReportsService {

    private final RegistrationRepository registrationRepository;
    private final MemberRepository memberRepository;
    private final CountyRepository countyRepository;
    private final FinanceTransactionRepository financeRepository;

    @Override
    public List<ChartDataPoint> getRegistrationTrends(String period) {
        LocalDateTime since = switch (period != null ? period : "30d") {
            case "7d" -> LocalDateTime.now().minusDays(7);
            case "6m" -> LocalDateTime.now().minusMonths(6);
            case "1y" -> LocalDateTime.now().minusYears(1);
            default -> LocalDateTime.now().minusDays(30);
        };
        try {
            return registrationRepository.dailyRegistrationsSince(since)
                    .stream().map(r -> new ChartDataPoint(r[0].toString(), (Number) r[1]))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Failed to load registration trends: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<ChartDataPoint> getMembershipBySection(String type) {
        try {
            return memberRepository.countBySection()
                    .stream().map(r -> new ChartDataPoint(r[0].toString(), (Number) r[1]))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Failed to load membership by section: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public ReportSummaryResponse getSummary(String period) {
        LocalDateTime since = switch (period != null ? period : "30d") {
            case "7d" -> LocalDateTime.now().minusDays(7);
            case "6m" -> LocalDateTime.now().minusMonths(6);
            case "1y" -> LocalDateTime.now().minusYears(1);
            default -> LocalDateTime.now().minusDays(30);
        };
        try {
            long totalMembers = memberRepository.count();
            long newRegistrations = registrationRepository.countSince(since);
            long pendingApprovals = registrationRepository.countByStatus(RegistrationStatus.PENDING);
            long totalRevenue = nonNull(financeRepository.totalByType(TransactionType.INCOME)).longValue();
            long activeCounties = memberRepository.countByCounty().size();
            long totalCounties = countyRepository.count();
            long revenueLastMonth = nonNull(financeRepository.totalByTypeSince(TransactionType.INCOME, LocalDateTime.now().minusDays(30))).longValue();
            long totalRevenueChange = totalRevenue - revenueLastMonth;

            return ReportSummaryResponse.builder()
                    .totalMembers(totalMembers)
                    .newRegistrations(newRegistrations)
                    .pendingApprovals(pendingApprovals)
                    .totalRevenue(totalRevenue)
                    .activeCounties(activeCounties)
                    .totalCounties(totalCounties)
                    .totalRevenueChange(totalRevenueChange)
                    .build();
        } catch (Exception e) {
            log.warn("Failed to load report summary: {}", e.getMessage());
            return ReportSummaryResponse.builder().build();
        }
    }

    @Override
    public List<ChartDataPoint> getCountyBreakdown() {
        try {
            return memberRepository.countByCounty()
                    .stream().map(r -> new ChartDataPoint(r[0].toString(), (Number) r[1]))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Failed to load county breakdown: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    private static BigDecimal nonNull(BigDecimal value) {
        return value != null ? value : BigDecimal.ZERO;
    }
}

package com.scout.management.service.impl;

import com.scout.management.dto.response.DashboardMetricsResponse.ChartDataPoint;
import com.scout.management.repository.MemberRepository;
import com.scout.management.repository.RegistrationRepository;
import com.scout.management.service.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportsServiceImpl implements ReportsService {

    private final RegistrationRepository registrationRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<ChartDataPoint> getRegistrationTrends(String period) {
        LocalDateTime since = switch (period != null ? period : "30d") {
            case "6m" -> LocalDateTime.now().minusMonths(6);
            case "1y" -> LocalDateTime.now().minusYears(1);
            default -> LocalDateTime.now().minusDays(30);
        };
        return registrationRepository.dailyRegistrationsSince(since)
                .stream().map(r -> new ChartDataPoint(r[0].toString(), (Number) r[1]))
                .collect(Collectors.toList());
    }

    @Override
    public List<ChartDataPoint> getMembershipBySection(String type) {
        return memberRepository.countBySection()
                .stream().map(r -> new ChartDataPoint(r[0].toString(), (Number) r[1]))
                .collect(Collectors.toList());
    }
}

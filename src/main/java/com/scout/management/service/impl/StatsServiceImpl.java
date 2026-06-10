package com.scout.management.service.impl;

import com.scout.management.dto.response.*;
import com.scout.management.enums.MemberStatus;
import com.scout.management.repository.*;
import com.scout.management.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final MemberRepository memberRepository;
    private final UnitRepository unitRepository;
    private final CountyRepository countyRepository;
    private final AuditLogRepository auditLogRepository;

    @Override
    public MemberStatsResponse getMemberStats() {
        long total = memberRepository.count();
        long active = memberRepository.countActiveMembers();
        long suspended = memberRepository.countByStatus(MemberStatus.SUSPENDED);
        return MemberStatsResponse.builder()
                .totalMembers(total).activeMembers(active).pendingRenewal(suspended).build();
    }

    @Override
    public UnitStatsResponse getUnitStats() {
        long totalUnits = unitRepository.countByIsActiveTrue();
        long totalCounties = countyRepository.count();
        var countyData = unitRepository.countByCounty();
        var topCounties = countyData.stream()
                .sorted((a, b) -> Long.compare(((Number) b[1]).longValue(), ((Number) a[1]).longValue()))
                .limit(5)
                .map(r -> new UnitStatsResponse.TopCounty(
                        r[0].toString(), ((Number) r[1]).longValue(), 0))
                .collect(Collectors.toList());
        return UnitStatsResponse.builder()
                .totalUnits(totalUnits).totalCounties(totalCounties)
                .averageUnitSize(totalCounties > 0 ? (double) totalUnits / totalCounties : 0)
                .topCounties(topCounties).build();
    }

    @Override
    public AuditStatsResponse getAuditStats() {
        long total = auditLogRepository.count();
        long success = auditLogRepository.countByAction("SUCCESS");
        return AuditStatsResponse.builder()
                .totalActivities(total)
                .successRate(total > 0 ? (double) success / total * 100 : 100)
                .securityAlerts(0).build();
    }
}

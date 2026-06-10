package com.scout.management.service;

import com.scout.management.dto.response.*;

public interface StatsService {
    MemberStatsResponse getMemberStats();
    UnitStatsResponse getUnitStats();
    AuditStatsResponse getAuditStats();
}

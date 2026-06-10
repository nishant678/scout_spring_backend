package com.scout.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder @AllArgsConstructor
public class MemberStatsResponse {
    private long totalMembers;
    private long activeMembers;
    private long pendingRenewal;
}

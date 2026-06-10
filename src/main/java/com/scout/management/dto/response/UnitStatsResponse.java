package com.scout.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @Builder @AllArgsConstructor
public class UnitStatsResponse {
    private long totalUnits;
    private long totalCounties;
    private double averageUnitSize;
    private List<TopCounty> topCounties;

    @Getter @Builder @AllArgsConstructor
    public static class TopCounty {
        private String name;
        private long units;
        private long members;
    }
}

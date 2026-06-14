package com.scout.management.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RegionalSettingsResponse {
    private String timezone;
    private String currency;
    private String dateFormat;
}
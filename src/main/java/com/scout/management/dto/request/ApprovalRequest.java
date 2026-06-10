package com.scout.management.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApprovalRequest {
    private Long registrationId;
    private String comment;
}

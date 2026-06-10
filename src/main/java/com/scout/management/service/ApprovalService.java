package com.scout.management.service;

import com.scout.management.dto.request.ApprovalRequest;
import com.scout.management.dto.response.ApprovalResponse;

import java.util.List;

public interface ApprovalService {
    ApprovalResponse approve(Long registrationId, ApprovalRequest request);
    ApprovalResponse reject(Long registrationId, ApprovalRequest request);
    List<ApprovalResponse> getHistory(Long registrationId);
}

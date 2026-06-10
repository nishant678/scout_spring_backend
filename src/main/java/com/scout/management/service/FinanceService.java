package com.scout.management.service;

import com.scout.management.dto.request.TransactionRequest;
import com.scout.management.dto.response.FinanceResponse;
import com.scout.management.dto.response.FinanceSummaryResponse;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.enums.TransactionType;

public interface FinanceService {
    PagedResponse<FinanceResponse> getAll(int page, int size, TransactionType type);
    FinanceResponse getById(Long id);
    FinanceResponse create(TransactionRequest request);
    FinanceSummaryResponse getSummary();
}

package com.scout.management.controller;

import com.scout.management.dto.request.TransactionRequest;
import com.scout.management.dto.response.ApiResponse;
import com.scout.management.dto.response.FinanceResponse;
import com.scout.management.dto.response.FinanceSummaryResponse;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.enums.TransactionType;
import com.scout.management.service.FinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
@Tag(name = "Finance", description = "Manage financial transactions and revenue summaries")
public class FinanceController {

    private final FinanceService financeService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @Operation(summary = "List transactions", description = "Returns paginated finance transactions, optionally filtered by type (INCOME/EXPENSE)")
    public ResponseEntity<ApiResponse<PagedResponse<FinanceResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TransactionType type) {
        return ResponseEntity.ok(ApiResponse.success("Transactions retrieved",
                financeService.getAll(page, size, type)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @Operation(summary = "Get transaction by ID", description = "Returns a single finance transaction")
    public ResponseEntity<ApiResponse<FinanceResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Transaction retrieved", financeService.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @Operation(summary = "Create a transaction", description = "Records a new income or expense transaction")
    public ResponseEntity<ApiResponse<FinanceResponse>> create(@Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Transaction created", financeService.create(request)));
    }

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @Operation(summary = "Get finance summary", description = "Returns aggregated revenue, expenses, balance, and breakdown")
    public ResponseEntity<ApiResponse<FinanceSummaryResponse>> getSummary() {
        return ResponseEntity.ok(ApiResponse.success("Finance summary retrieved", financeService.getSummary()));
    }
}

package com.scout.management.dto.response;

import com.scout.management.entity.FinanceTransactionEntity;
import com.scout.management.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class FinanceResponse {
    private Long id;
    private TransactionType type;
    private BigDecimal amount;
    private String description;
    private String category;
    private String referenceNumber;
    private String memberName;
    private LocalDateTime createdAt;

    public static FinanceResponse from(FinanceTransactionEntity e) {
        return FinanceResponse.builder()
                .id(e.getId()).type(e.getType()).amount(e.getAmount())
                .description(e.getDescription()).category(e.getCategory())
                .referenceNumber(e.getReferenceNumber())
                .memberName(e.getMember() != null
                        ? e.getMember().getFirstName() + " " + e.getMember().getLastName()
                        : null)
                .createdAt(e.getCreatedAt()).build();
    }
}

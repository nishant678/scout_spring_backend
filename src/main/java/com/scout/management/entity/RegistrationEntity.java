package com.scout.management.entity;

import com.scout.management.enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "registrations", indexes = {
        @Index(name = "idx_reg_status", columnList = "status"),
        @Index(name = "idx_reg_email", columnList = "email"),
        @Index(name = "idx_reg_county", columnList = "county")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    private String phone;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private String county;

    @Enumerated(EnumType.STRING)
    private RegistrationStatus status;

    @Column(name = "submission_date")
    private LocalDateTime submissionDate;

    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (status == null) status = RegistrationStatus.PENDING;
        if (submissionDate == null) submissionDate = LocalDateTime.now();
    }
}

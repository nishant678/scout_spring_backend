package com.scout.management.entity;

import com.scout.management.enums.MemberStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "members", indexes = {
        @Index(name = "idx_member_email", columnList = "email"),
        @Index(name = "idx_member_status", columnList = "status"),
        @Index(name = "idx_member_county", columnList = "county"),
        @Index(name = "idx_member_section", columnList = "section")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Column(nullable = false)
    private String section;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private String county;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String address;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (status == null) status = MemberStatus.ACTIVE;
    }
}

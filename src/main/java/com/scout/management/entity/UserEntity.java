package com.scout.management.entity;

import com.scout.management.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "user_id", unique = true)
    private String userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "is_active")
    private boolean isActive;

    private String phone;

    @Column(name = "notification_email")
    private Boolean notificationEmail;

    @Column(name = "notification_sms")
    private Boolean notificationSms;

    @Column(name = "notification_security")
    private Boolean notificationSecurity;

    @ColumnDefault("'Africa/Nairobi'")
    private String timezone;

    @ColumnDefault("'KES'")
    private String currency;

    @ColumnDefault("'MMM d, yyyy'")
    @Column(name = "date_format")
    private String dateFormat;

    @Column(name = "created_by")
    private Long createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        if (!isActive) isActive = true;
        if (userId == null) {
            userId = "KS" + (System.currentTimeMillis() % 100000);
        }
    }
}

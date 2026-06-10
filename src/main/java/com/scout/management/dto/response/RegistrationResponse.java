package com.scout.management.dto.response;

import com.scout.management.entity.RegistrationEntity;
import com.scout.management.enums.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class RegistrationResponse {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String section;
    private String unit;
    private String county;
    private RegistrationStatus status;
    private LocalDateTime submissionDate;
    private String notes;
    private LocalDateTime createdAt;

    public static RegistrationResponse from(RegistrationEntity e) {
        return RegistrationResponse.builder()
                .id(e.getId()).name(e.getName()).email(e.getEmail()).phone(e.getPhone())
                .section(e.getSection()).unit(e.getUnit()).county(e.getCounty())
                .status(e.getStatus()).submissionDate(e.getSubmissionDate())
                .notes(e.getNotes()).createdAt(e.getCreatedAt()).build();
    }
}

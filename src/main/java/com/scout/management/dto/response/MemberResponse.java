package com.scout.management.dto.response;

import com.scout.management.entity.MemberEntity;
import com.scout.management.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String section;
    private String unit;
    private String county;
    private MemberStatus status;
    private LocalDate dateOfBirth;
    private String address;
    private LocalDateTime createdAt;

    public static MemberResponse from(MemberEntity e) {
        return MemberResponse.builder()
                .id(e.getId()).firstName(e.getFirstName()).lastName(e.getLastName())
                .email(e.getEmail()).phone(e.getPhone()).section(e.getSection())
                .unit(e.getUnit()).county(e.getCounty()).status(e.getStatus())
                .dateOfBirth(e.getDateOfBirth()).address(e.getAddress()).createdAt(e.getCreatedAt())
                .build();
    }
}

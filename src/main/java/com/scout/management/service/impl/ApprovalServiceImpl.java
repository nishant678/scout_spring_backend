package com.scout.management.service.impl;

import com.scout.management.dto.request.ApprovalRequest;
import com.scout.management.dto.response.ApprovalResponse;
import com.scout.management.entity.ApprovalEntity;
import com.scout.management.entity.RegistrationEntity;
import com.scout.management.entity.UserEntity;
import com.scout.management.enums.RegistrationStatus;
import com.scout.management.exception.BadRequestException;
import com.scout.management.exception.ResourceNotFoundException;
import com.scout.management.repository.ApprovalRepository;
import com.scout.management.repository.RegistrationRepository;
import com.scout.management.service.ApprovalService;
import com.scout.management.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final RegistrationRepository registrationRepository;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public ApprovalResponse approve(Long registrationId, ApprovalRequest request) {
        var registration = findRegistration(registrationId);
        if (registration.getStatus() != RegistrationStatus.PENDING) {
            throw new BadRequestException("Registration is already " + registration.getStatus().name().toLowerCase());
        }

        registration.setStatus(RegistrationStatus.APPROVED);
        registrationRepository.save(registration);

        return saveApproval(registration, RegistrationStatus.APPROVED, request.getComment());
    }

    @Override
    @Transactional
    public ApprovalResponse reject(Long registrationId, ApprovalRequest request) {
        var registration = findRegistration(registrationId);
        if (registration.getStatus() != RegistrationStatus.PENDING) {
            throw new BadRequestException("Registration is already " + registration.getStatus().name().toLowerCase());
        }

        registration.setStatus(RegistrationStatus.REJECTED);
        registrationRepository.save(registration);

        return saveApproval(registration, RegistrationStatus.REJECTED, request.getComment());
    }

    @Override
    public List<ApprovalResponse> getHistory(Long registrationId) {
        return approvalRepository.findByRegistrationIdOrderByCreatedAtDesc(registrationId)
                .stream().map(ApprovalResponse::from).toList();
    }

    private ApprovalResponse saveApproval(RegistrationEntity registration, RegistrationStatus status, String comment) {
        UserEntity currentUser = securityUtil.getCurrentUser();
        var approval = approvalRepository.save(ApprovalEntity.builder()
                .registration(registration).approvedBy(currentUser)
                .status(status).comment(comment).build());
        return ApprovalResponse.from(approval);
    }

    private RegistrationEntity findRegistration(Long id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found with id: " + id));
    }
}

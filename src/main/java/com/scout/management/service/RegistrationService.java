package com.scout.management.service;

import com.scout.management.dto.request.RegistrationRequest;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.dto.response.RegistrationResponse;
import com.scout.management.enums.RegistrationStatus;

public interface RegistrationService {
    PagedResponse<RegistrationResponse> getAll(int page, int size, String status);
    RegistrationResponse getById(Long id);
    RegistrationResponse create(RegistrationRequest request);
    RegistrationResponse update(Long id, RegistrationRequest request);
    void delete(Long id);
    long countPending();
}

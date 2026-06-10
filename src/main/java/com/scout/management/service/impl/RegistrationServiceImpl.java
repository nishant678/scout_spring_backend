package com.scout.management.service.impl;

import com.scout.management.dto.request.RegistrationRequest;
import com.scout.management.dto.response.RegistrationResponse;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.entity.RegistrationEntity;
import com.scout.management.enums.RegistrationStatus;
import com.scout.management.exception.ResourceNotFoundException;
import com.scout.management.repository.RegistrationRepository;
import com.scout.management.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;

    @Override
    public PagedResponse<RegistrationResponse> getAll(int page, int size, String status) {
        var pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        var result = status != null && !status.isBlank()
                ? registrationRepository.findByStatus(RegistrationStatus.valueOf(status.toUpperCase()), pageable)
                : registrationRepository.findAll(pageable);
        var content = result.getContent().stream().map(RegistrationResponse::from).toList();
        return PagedResponse.from(result, content);
    }

    @Override
    public RegistrationResponse getById(Long id) {
        return RegistrationResponse.from(findById(id));
    }

    @Override
    public RegistrationResponse create(RegistrationRequest request) {
        var entity = registrationRepository.save(RegistrationEntity.builder()
                .name(request.getName()).email(request.getEmail()).phone(request.getPhone())
                .section(request.getSection()).unit(request.getUnit()).county(request.getCounty())
                .notes(request.getNotes()).status(RegistrationStatus.PENDING).build());
        return RegistrationResponse.from(entity);
    }

    @Override
    public RegistrationResponse update(Long id, RegistrationRequest request) {
        var entity = findById(id);
        entity.setName(request.getName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setSection(request.getSection());
        entity.setUnit(request.getUnit());
        entity.setCounty(request.getCounty());
        entity.setNotes(request.getNotes());
        return RegistrationResponse.from(registrationRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        registrationRepository.deleteById(id);
    }

    @Override
    public long countPending() {
        return registrationRepository.countByStatus(RegistrationStatus.PENDING);
    }

    private RegistrationEntity findById(Long id) {
        return registrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registration not found with id: " + id));
    }
}

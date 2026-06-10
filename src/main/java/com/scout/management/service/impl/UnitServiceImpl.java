package com.scout.management.service.impl;

import com.scout.management.dto.request.UnitRequest;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.dto.response.UnitResponse;
import com.scout.management.entity.UnitEntity;
import com.scout.management.exception.DuplicateResourceException;
import com.scout.management.exception.ResourceNotFoundException;
import com.scout.management.repository.UnitRepository;
import com.scout.management.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    @Override
    public PagedResponse<UnitResponse> getAll(int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        var result = unitRepository.findAll(pageable);
        return PagedResponse.from(result, result.getContent().stream().map(UnitResponse::from).toList());
    }

    @Override
    public UnitResponse getById(Long id) {
        return UnitResponse.from(findById(id));
    }

    @Override
    public UnitResponse create(UnitRequest request) {
        if (unitRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Unit code " + request.getCode() + " already exists");
        }

        var entity = unitRepository.save(UnitEntity.builder()
                .name(request.getName()).code(request.getCode().toUpperCase())
                .county(request.getCounty()).coordinator(request.getCoordinator())
                .address(request.getAddress()).isActive(true).build());
        return UnitResponse.from(entity);
    }

    @Override
    public UnitResponse update(Long id, UnitRequest request) {
        var entity = findById(id);

        if (!entity.getCode().equals(request.getCode()) && unitRepository.existsByCode(request.getCode())) {
            throw new DuplicateResourceException("Unit code " + request.getCode() + " already exists");
        }

        entity.setName(request.getName());
        entity.setCode(request.getCode().toUpperCase());
        entity.setCounty(request.getCounty());
        entity.setCoordinator(request.getCoordinator());
        entity.setAddress(request.getAddress());

        return UnitResponse.from(unitRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var entity = findById(id);
        entity.setActive(false);
        unitRepository.save(entity);
    }

    @Override
    public long count() {
        return unitRepository.countByIsActiveTrue();
    }

    private UnitEntity findById(Long id) {
        return unitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unit not found with id: " + id));
    }
}

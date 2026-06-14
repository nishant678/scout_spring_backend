package com.scout.management.service.impl;

import com.scout.management.dto.request.CountyRequest;
import com.scout.management.dto.response.CountyResponse;
import com.scout.management.entity.CountyEntity;
import com.scout.management.exception.DuplicateResourceException;
import com.scout.management.exception.ResourceNotFoundException;
import com.scout.management.repository.CountyRepository;
import com.scout.management.service.CountyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountyServiceImpl implements CountyService {

    private final CountyRepository countyRepository;

    @Override
    public List<CountyResponse> getAll() {
        return countyRepository.findAll().stream().map(CountyResponse::from).toList();
    }

    @Override
    public CountyResponse getById(Long id) {
        return CountyResponse.from(findById(id));
    }

    @Override
    @Transactional
    public CountyResponse create(CountyRequest request) {
        if (countyRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("County '" + request.getName() + "' already exists");
        }
        var entity = countyRepository.save(CountyEntity.builder()
                .name(request.getName()).code(request.getCode().toUpperCase()).build());
        return CountyResponse.from(entity);
    }

    @Override
    @Transactional
    public CountyResponse update(Long id, CountyRequest request) {
        var entity = findById(id);
        if (!entity.getName().equals(request.getName()) && countyRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("County '" + request.getName() + "' already exists");
        }
        entity.setName(request.getName());
        entity.setCode(request.getCode().toUpperCase());
        return CountyResponse.from(countyRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var entity = findById(id);
        entity.setActive(false);
        countyRepository.save(entity);
    }

    private CountyEntity findById(Long id) {
        return countyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("County not found with id: " + id));
    }
}
package com.scout.management.service.impl;

import com.scout.management.dto.request.SectionRequest;
import com.scout.management.dto.response.SectionResponse;
import com.scout.management.entity.SectionEntity;
import com.scout.management.exception.DuplicateResourceException;
import com.scout.management.exception.ResourceNotFoundException;
import com.scout.management.repository.SectionRepository;
import com.scout.management.service.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionServiceImpl implements SectionService {

    private final SectionRepository sectionRepository;

    @Override
    public List<SectionResponse> getAll() {
        return sectionRepository.findAll().stream().map(SectionResponse::from).toList();
    }

    @Override
    public SectionResponse getById(Long id) {
        return SectionResponse.from(findById(id));
    }

    @Override
    @Transactional
    public SectionResponse create(SectionRequest request) {
        if (sectionRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Section '" + request.getName() + "' already exists");
        }
        var entity = sectionRepository.save(SectionEntity.builder()
                .name(request.getName()).description(request.getDescription()).build());
        return SectionResponse.from(entity);
    }

    @Override
    @Transactional
    public SectionResponse update(Long id, SectionRequest request) {
        var entity = findById(id);
        if (!entity.getName().equals(request.getName()) && sectionRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Section '" + request.getName() + "' already exists");
        }
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return SectionResponse.from(sectionRepository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        var entity = findById(id);
        entity.setActive(false);
        sectionRepository.save(entity);
    }

    private SectionEntity findById(Long id) {
        return sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found with id: " + id));
    }
}
package com.scout.management.service;

import com.scout.management.dto.request.SectionRequest;
import com.scout.management.dto.response.SectionResponse;

import java.util.List;

public interface SectionService {
    List<SectionResponse> getAll();
    SectionResponse getById(Long id);
    SectionResponse create(SectionRequest request);
    SectionResponse update(Long id, SectionRequest request);
    void delete(Long id);
}
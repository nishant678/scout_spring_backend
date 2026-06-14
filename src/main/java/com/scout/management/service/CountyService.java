package com.scout.management.service;

import com.scout.management.dto.request.CountyRequest;
import com.scout.management.dto.response.CountyResponse;

import java.util.List;

public interface CountyService {
    List<CountyResponse> getAll();
    CountyResponse getById(Long id);
    CountyResponse create(CountyRequest request);
    CountyResponse update(Long id, CountyRequest request);
    void delete(Long id);
}
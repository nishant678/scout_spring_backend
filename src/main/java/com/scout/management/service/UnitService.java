package com.scout.management.service;

import com.scout.management.dto.request.UnitRequest;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.dto.response.UnitResponse;

public interface UnitService {
    PagedResponse<UnitResponse> getAll(int page, int size);
    UnitResponse getById(Long id);
    UnitResponse create(UnitRequest request);
    UnitResponse update(Long id, UnitRequest request);
    void delete(Long id);
    long count();
}

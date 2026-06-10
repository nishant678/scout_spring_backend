package com.scout.management.service;

import com.scout.management.dto.request.MemberRequest;
import com.scout.management.dto.response.MemberResponse;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.enums.MemberStatus;

public interface MemberService {
    PagedResponse<MemberResponse> getAll(int page, int size, String search);
    MemberResponse getById(Long id);
    MemberResponse create(MemberRequest request);
    MemberResponse update(Long id, MemberRequest request);
    void delete(Long id);
    long countByStatus(MemberStatus status);
}

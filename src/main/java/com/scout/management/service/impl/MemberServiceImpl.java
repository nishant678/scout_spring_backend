package com.scout.management.service.impl;

import com.scout.management.dto.request.MemberRequest;
import com.scout.management.dto.response.MemberResponse;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.entity.MemberEntity;
import com.scout.management.enums.MemberStatus;
import com.scout.management.exception.DuplicateResourceException;
import com.scout.management.exception.ResourceNotFoundException;
import com.scout.management.repository.MemberRepository;
import com.scout.management.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public PagedResponse<MemberResponse> getAll(int page, int size, String search) {
        var pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        var result = memberRepository.findAll(pageable);
        var content = result.getContent().stream().map(MemberResponse::from).toList();
        return PagedResponse.from(result, content);
    }

    @Override
    public MemberResponse getById(Long id) {
        return MemberResponse.from(findById(id));
    }

    @Override
    public MemberResponse create(MemberRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Member with email " + request.getEmail() + " already exists");
        }

        var entity = memberRepository.save(MemberEntity.builder()
                .firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).phone(request.getPhone())
                .section(request.getSection()).unit(request.getUnit())
                .county(request.getCounty()).dateOfBirth(request.getDateOfBirth())
                .address(request.getAddress()).status(MemberStatus.ACTIVE).build());

        return MemberResponse.from(entity);
    }

    @Override
    public MemberResponse update(Long id, MemberRequest request) {
        var entity = findById(id);

        if (!entity.getEmail().equals(request.getEmail())
                && memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already in use by another member");
        }

        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setSection(request.getSection());
        entity.setUnit(request.getUnit());
        entity.setCounty(request.getCounty());
        entity.setDateOfBirth(request.getDateOfBirth());
        entity.setAddress(request.getAddress());

        return MemberResponse.from(memberRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        var entity = findById(id);
        entity.setStatus(MemberStatus.INACTIVE);
        memberRepository.save(entity);
    }

    @Override
    public long countByStatus(MemberStatus status) {
        return memberRepository.countByStatus(status);
    }

    private MemberEntity findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }
}

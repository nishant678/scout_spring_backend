package com.scout.management.controller;

import com.scout.management.dto.request.MemberRequest;
import com.scout.management.dto.response.ApiResponse;
import com.scout.management.dto.response.MemberResponse;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Tag(name = "Members", description = "CRUD operations for scout members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    @Operation(summary = "List members", description = "Returns paginated list of members, optionally filtered by search term")
    public ResponseEntity<ApiResponse<PagedResponse<MemberResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(ApiResponse.success("Members retrieved", memberService.getAll(page, size, search)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get member by ID", description = "Returns a single member's details")
    public ResponseEntity<ApiResponse<MemberResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Member retrieved", memberService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "Create a member", description = "Adds a new scout member to the system")
    public ResponseEntity<ApiResponse<MemberResponse>> create(@Valid @RequestBody MemberRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Member created", memberService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a member", description = "Updates an existing member's information")
    public ResponseEntity<ApiResponse<MemberResponse>> update(@PathVariable Long id, @Valid @RequestBody MemberRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Member updated", memberService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate a member", description = "Soft-deletes a member (sets status to INACTIVE)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Member deactivated"));
    }
}

package com.scout.management.controller;

import com.scout.management.dto.request.CountyRequest;
import com.scout.management.dto.response.ApiResponse;
import com.scout.management.dto.response.CountyResponse;
import com.scout.management.service.CountyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counties")
@RequiredArgsConstructor
@Tag(name = "Counties", description = "CRUD operations for counties")
public class CountyController {

    private final CountyService countyService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LEADER')")
    @Operation(summary = "List all counties")
    public ResponseEntity<ApiResponse<List<CountyResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Counties retrieved", countyService.getAll()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LEADER')")
    @Operation(summary = "Get county by ID")
    public ResponseEntity<ApiResponse<CountyResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("County retrieved", countyService.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LEADER')")
    @Operation(summary = "Create a county")
    public ResponseEntity<ApiResponse<CountyResponse>> create(@Valid @RequestBody CountyRequest request) {
        return ResponseEntity.ok(ApiResponse.success("County created", countyService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LEADER')")
    @Operation(summary = "Update a county")
    public ResponseEntity<ApiResponse<CountyResponse>> update(@PathVariable Long id, @Valid @RequestBody CountyRequest request) {
        return ResponseEntity.ok(ApiResponse.success("County updated", countyService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @Operation(summary = "Deactivate a county")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        countyService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("County deactivated"));
    }
}

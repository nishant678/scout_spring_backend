package com.scout.management.controller;

import com.scout.management.dto.request.UnitRequest;
import com.scout.management.dto.response.ApiResponse;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.dto.response.UnitResponse;
import com.scout.management.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/units")
@RequiredArgsConstructor
@Tag(name = "Units", description = "CRUD operations for scout units")
public class UnitController {

    private final UnitService unitService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LEADER')")
    @Operation(summary = "List units", description = "Returns paginated list of scout units")
    public ResponseEntity<ApiResponse<PagedResponse<UnitResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success("Units retrieved", unitService.getAll(page, size)));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LEADER')")
    @Operation(summary = "Get unit by ID", description = "Returns a single unit's details")
    public ResponseEntity<ApiResponse<UnitResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Unit retrieved", unitService.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LEADER')")
    @Operation(summary = "Create a unit", description = "Adds a new scout unit to the system")
    public ResponseEntity<ApiResponse<UnitResponse>> create(@Valid @RequestBody UnitRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Unit created", unitService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'LEADER')")
    @Operation(summary = "Update a unit", description = "Updates an existing unit's information")
    public ResponseEntity<ApiResponse<UnitResponse>> update(@PathVariable Long id, @Valid @RequestBody UnitRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Unit updated", unitService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @Operation(summary = "Deactivate a unit", description = "Soft-deletes a unit (sets isActive to false)")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        unitService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Unit deactivated"));
    }
}

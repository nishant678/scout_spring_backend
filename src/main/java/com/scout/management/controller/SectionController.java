package com.scout.management.controller;

import com.scout.management.dto.request.SectionRequest;
import com.scout.management.dto.response.ApiResponse;
import com.scout.management.dto.response.SectionResponse;
import com.scout.management.service.SectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
@RequiredArgsConstructor
@Tag(name = "Sections", description = "CRUD operations for scout sections")
public class SectionController {

    private final SectionService sectionService;

    @GetMapping
    @Operation(summary = "List all sections")
    public ResponseEntity<ApiResponse<List<SectionResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Sections retrieved", sectionService.getAll()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get section by ID")
    public ResponseEntity<ApiResponse<SectionResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Section retrieved", sectionService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "Create a section")
    public ResponseEntity<ApiResponse<SectionResponse>> create(@Valid @RequestBody SectionRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Section created", sectionService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a section")
    public ResponseEntity<ApiResponse<SectionResponse>> update(@PathVariable Long id, @Valid @RequestBody SectionRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Section updated", sectionService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate a section")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        sectionService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Section deactivated"));
    }
}

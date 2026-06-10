package com.scout.management.controller;

import com.scout.management.dto.response.ApiResponse;
import com.scout.management.entity.SectionEntity;
import com.scout.management.repository.SectionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
@RequiredArgsConstructor
@Tag(name = "Sections", description = "Manage scout sections (Beavers, Cubs, Scouts, Ventures, Rovers)")
public class SectionController {

    private final SectionRepository sectionRepository;

    @GetMapping
    @Operation(summary = "List all sections", description = "Returns all scout sections in the system")
    public ResponseEntity<ApiResponse<List<SectionEntity>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Sections retrieved", sectionRepository.findAll()));
    }

    @PostMapping
    @Operation(summary = "Create a section", description = "Adds a new scout section")
    public ResponseEntity<ApiResponse<SectionEntity>> create(@RequestBody SectionEntity section) {
        return ResponseEntity.ok(ApiResponse.success("Section created", sectionRepository.save(section)));
    }
}

package com.scout.management.controller;

import com.scout.management.dto.response.ApiResponse;
import com.scout.management.entity.CountyEntity;
import com.scout.management.repository.CountyRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/counties")
@RequiredArgsConstructor
@Tag(name = "Counties", description = "Manage Kenyan counties for scout regions")
public class CountyController {

    private final CountyRepository countyRepository;

    @GetMapping
    @Operation(summary = "List all counties", description = "Returns all counties in the system")
    public ResponseEntity<ApiResponse<List<CountyEntity>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Counties retrieved", countyRepository.findAll()));
    }

    @PostMapping
    @Operation(summary = "Create a county", description = "Adds a new county to the system")
    public ResponseEntity<ApiResponse<CountyEntity>> create(@RequestBody CountyEntity county) {
        return ResponseEntity.ok(ApiResponse.success("County created", countyRepository.save(county)));
    }
}

package com.scout.management.controller;

import com.scout.management.dto.request.RegistrationRequest;
import com.scout.management.dto.response.ApiResponse;
import com.scout.management.dto.response.PagedResponse;
import com.scout.management.dto.response.RegistrationResponse;
import com.scout.management.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/registrations")
@RequiredArgsConstructor
@Tag(name = "Registrations", description = "Manage member registration requests")
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping
    @Operation(summary = "List registrations", description = "Returns paginated registrations, optionally filtered by status (PENDING/APPROVED/REJECTED)")
    public ResponseEntity<ApiResponse<PagedResponse<RegistrationResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status) {
        return ResponseEntity.ok(ApiResponse.success("Registrations retrieved",
                registrationService.getAll(page, size, status)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get registration by ID", description = "Returns a single registration request")
    public ResponseEntity<ApiResponse<RegistrationResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Registration retrieved", registrationService.getById(id)));
    }

    @PostMapping
    @Operation(summary = "Create a registration", description = "Submits a new member registration request")
    public ResponseEntity<ApiResponse<RegistrationResponse>> create(@Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Registration created", registrationService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a registration", description = "Updates an existing registration request")
    public ResponseEntity<ApiResponse<RegistrationResponse>> update(
            @PathVariable Long id, @Valid @RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Registration updated", registrationService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a registration", description = "Permanently removes a registration request")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        registrationService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Registration deleted"));
    }
}

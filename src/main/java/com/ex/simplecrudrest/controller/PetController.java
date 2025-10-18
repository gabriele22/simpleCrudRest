package com.ex.simplecrudrest.controller;

import com.ex.simplecrudrest.dto.PetRequest;
import com.ex.simplecrudrest.dto.PetResponse;
import com.ex.simplecrudrest.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * REST Controller for Pet management operations.
 * Provides endpoints for CRUD operations on Pet resources.
 */
@RestController
@RequestMapping("/api/v1/pets")
@RequiredArgsConstructor
@Tag(name = "Pet Management", description = "APIs for managing pets")
public class PetController {

    private final PetService petService;

    /**
     * Creates a new pet
     */
    @PostMapping
    @Operation(summary = "Create a new pet", description = "Creates a new pet and returns the created resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<PetResponse> createPet(@Valid @RequestBody PetRequest request) {
        PetResponse createdPet = petService.createPet(request);

        // Build the Location header with the URI of the created resource
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdPet.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdPet);
    }

    /**
     * Retrieves a pet by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get pet by ID", description = "Retrieves a pet by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet found"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    public ResponseEntity<PetResponse> getPetById(@PathVariable Long id) {
        PetResponse pet = petService.getPetById(id);
        return ResponseEntity.ok(pet);
    }

    /**
     * Retrieves all pets
     */
    @GetMapping
    @Operation(summary = "Get all pets", description = "Retrieves a list of all pets")
    @ApiResponse(responseCode = "200", description = "List of pets retrieved successfully")
    public ResponseEntity<List<PetResponse>> getAllPets() {
        List<PetResponse> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }

    /**
     * Updates an existing pet
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a pet", description = "Updates an existing pet by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    public ResponseEntity<PetResponse> updatePet(
            @PathVariable Long id,
            @Valid @RequestBody PetRequest request) {
        PetResponse updatedPet = petService.updatePet(id, request);
        return ResponseEntity.ok(updatedPet);
    }

    /**
     * Deletes a pet by ID
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a pet", description = "Deletes a pet by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pet deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Pet not found")
    })
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Retrieves a pet by ID
     */
    @GetMapping("/species")
    @Operation(summary = "Get number of different species",
            description = "Retrieves number of different species present in the db")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    public ResponseEntity<Integer> getDifferentSpecies() {
        int numberOfDifferentSpecies = petService.getNumberOfDifferentSpecies();
        return ResponseEntity.ok(numberOfDifferentSpecies);
    }
}
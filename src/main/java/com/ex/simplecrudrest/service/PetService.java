package com.ex.simplecrudrest.service;

import com.ex.simplecrudrest.dto.PetRequest;
import com.ex.simplecrudrest.dto.PetResponse;

import java.util.List;

/**
 * Service interface for Pet business logic operations.
 * This layer encapsulates business rules and coordinates between
 * the controller and repository layers.
 */
public interface PetService {

    /**
     * Creates a new pet
     * @param request the pet creation request
     * @return the created pet response
     */
    PetResponse createPet(PetRequest request);

    /**
     * Retrieves a pet by its ID
     * @param id the pet ID
     * @return the pet response
     * @throws PetNotFoundException if pet not found
     */
    PetResponse getPetById(Long id);

    /**
     * Retrieves all pets
     * @return list of all pet responses
     */
    List<PetResponse> getAllPets();

    /**
     * Updates an existing pet
     * @param id the pet ID to update
     * @param request the pet update request
     * @return the updated pet response
     * @throws PetNotFoundException if pet not found
     */
    PetResponse updatePet(Long id, PetRequest request);

    /**
     * Deletes a pet by its ID
     * @param id the pet ID to delete
     * @throws PetNotFoundException if pet not found
     */
    void deletePet(Long id);
}
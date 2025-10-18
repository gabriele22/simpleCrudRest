package com.ex.simplecrudrest.dao;

import com.ex.simplecrudrest.model.Pet;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Pet data access operations.
 * This abstraction allows easy switching between different persistence mechanisms
 * (e.g., from relational to non-relational databases) without affecting the service layer.
 */
public interface PetDao extends Dao<Pet, Long>{

    /**
     * Saves a new pet or updates an existing one
     * @param pet the pet to save
     * @return the saved pet with generated ID
     */
    Pet save(Pet pet);

    /**
     * Finds a pet by its ID
     * @param id the pet ID
     * @return an Optional containing the pet if found, empty otherwise
     */
    Optional<Pet> findById(Long id);

    /**
     * Retrieves all pets
     * @return list of all pets
     */
    List<Pet> findAll();

    /**
     * Deletes a pet by its ID
     * @param id the pet ID
     */
    void deleteById(Long id);

    /**
     * Checks if a pet exists by its ID
     * @param id the pet ID
     * @return true if the pet exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     *  @return number of different species
     */

    int getNumberOfDifferentSpecies();
}
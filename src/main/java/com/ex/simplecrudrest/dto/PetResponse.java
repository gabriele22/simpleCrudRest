package com.ex.simplecrudrest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Pet responses.
 * Used to decouple the internal domain model from the API contract.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetResponse {

    /**
     * Unique identifier of the pet
     */
    private Long id;

    /**
     * Name of the pet
     */
    private String name;

    /**
     * Species of the pet
     */
    private String species;

    /**
     * Age of the pet (can be null)
     */
    private Integer age;

    /**
     * Name of the pet's owner (can be null)
     */
    private String ownerName;
}
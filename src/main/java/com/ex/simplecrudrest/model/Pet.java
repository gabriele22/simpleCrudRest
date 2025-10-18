package com.ex.simplecrudrest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pet domain model representing a pet entity.
 * This is a pure domain object (POJO) without any persistence framework annotations,
 * making it completely database-agnostic and easy to adapt to any persistence mechanism
 * (relational, non-relational, in-memory, etc.).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet implements PersistenceObject {

    /**
     * Unique identifier for the pet
     */
    private Long id;

    /**
     * Name of the pet (required)
     */
    private String name;

    /**
     * Species of the pet (e.g., "Dog", "Cat", "Rabbit") (required)
     */
    private String species;

    /**
     * Age of the pet in years (optional, must be >= 0 if provided)
     */
    private Integer age;

    /**
     * Name of the pet's owner (optional)
     */
    private String ownerName;
}
package com.ex.simplecrudrest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pet domain model representing a pet entity.
 * This is a plain Java object (POJO) without JPA annotations,
 * making it database-agnostic and easy to adapt to any persistence mechanism.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pet {

    /**
     * Unique identifier for the pet (auto-generated)
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
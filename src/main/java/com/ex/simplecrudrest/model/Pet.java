package com.ex.simplecrudrest.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
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
@Entity
@Table(name = "pets")
public class Pet {

    /**
     * Unique identifier for the pet (auto-generated)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Name of the pet (required)
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Species of the pet (e.g., "Dog", "Cat", "Rabbit") (required)
     */
    @Column(name = "species", nullable = false, length = 50)
    private String species;

    /**
     * Age of the pet in years (optional, must be >= 0 if provided)
     */
    @Column(name = "age")
    private Integer age;

    /**
     * Name of the pet's owner (optional)
     */
    @Column(name = "owner_name", length = 100)
    private String ownerName;
}
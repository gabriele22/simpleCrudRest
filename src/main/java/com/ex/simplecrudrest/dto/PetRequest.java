package com.ex.simplecrudrest.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Pet creation and update requests.
 * Contains validation annotations to ensure data integrity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PetRequest {

    /**
     * Name of the pet (required)
     */
    @NotBlank(message = "Pet name is required")
    private String name;

    /**
     * Species of the pet (required)
     */
    @NotBlank(message = "Pet species is required")
    private String species;

    /**
     * Age of the pet (optional, must be >= 0 if provided)
     */
    @Min(value = 0, message = "Pet age must be greater than or equal to 0")
    private Integer age;

    /**
     * Name of the pet's owner (optional)
     */
    private String ownerName;
}

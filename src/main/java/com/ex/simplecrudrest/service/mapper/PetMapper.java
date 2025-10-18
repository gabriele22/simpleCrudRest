package com.ex.simplecrudrest.service.mapper;

import com.ex.simplecrudrest.dto.PetRequest;
import com.ex.simplecrudrest.dto.PetResponse;
import com.ex.simplecrudrest.model.Pet;

/**
 * Mapper utility class for converting between Pet domain model and DTOs.
 * This centralizes the mapping logic and makes it easier to maintain.
 */
public class PetMapper {

    private PetMapper(){
        /*private constructor for a utility class with only static method*/
    }

    /**
     * Converts a PetRequest to a Pet domain model
     * @param request the PetRequest DTO
     * @return the Pet domain model
     */
    public static Pet fromDtoRequestToEntity(PetRequest request) {
        return Pet.builder()
                .name(request.getName())
                .species(request.getSpecies())
                .age(request.getAge())
                .ownerName(request.getOwnerName())
                .build();
    }

    /**
     * Updates an existing Pet with data from a PetRequest
     * @param pet the existing Pet to update
     * @param request the PetRequest with new data
     */
    public static void updateEntityFromRequest(Pet pet, PetRequest request) {
        pet.setName(request.getName());
        pet.setSpecies(request.getSpecies());
        pet.setAge(request.getAge());
        pet.setOwnerName(request.getOwnerName());
    }

    /**
     * Converts a Pet domain model to a PetResponse DTO
     * @param pet the Pet domain model
     * @return the PetResponse DTO
     */
    public static PetResponse fromEntityToDtoResponse(Pet pet) {
        return PetResponse.builder()
                .id(pet.getId())
                .name(pet.getName())
                .species(pet.getSpecies())
                .age(pet.getAge())
                .ownerName(pet.getOwnerName())
                .build();
    }
}
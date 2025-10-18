package com.ex.simplecrudrest.dao.mapper;

import com.ex.simplecrudrest.dao.entity.PetJpaEntity;
import com.ex.simplecrudrest.model.Pet;

/**
 * Mapper for converting between Pet domain model and PetJpaEntity persistence model.
 * This separation allows the domain model to remain completely independent of
 * persistence framework concerns (JPA, MongoDB, etc.).
 */
public class PetEntityMapper {

    private PetEntityMapper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a Pet domain model to a PetJpaEntity for persistence
     * @param pet the domain model
     * @return the JPA entity
     */
    public static PetJpaEntity toJpaEntity(Pet pet) {
        if (pet == null) {
            return null;
        }

        return PetJpaEntity.builder()
                .id(pet.getId())
                .name(pet.getName())
                .species(pet.getSpecies())
                .age(pet.getAge())
                .ownerName(pet.getOwnerName())
                .build();
    }

    /**
     * Converts a PetJpaEntity from persistence to a Pet domain model
     * @param entity the JPA entity
     * @return the domain model
     */
    public static Pet toDomainModel(PetJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return Pet.builder()
                .id(entity.getId())
                .name(entity.getName())
                .species(entity.getSpecies())
                .age(entity.getAge())
                .ownerName(entity.getOwnerName())
                .build();
    }
}

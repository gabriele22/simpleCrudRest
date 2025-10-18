package com.ex.simplecrudrest.dao.mapper;

import com.ex.simplecrudrest.dao.document.PetMongoDocument;
import com.ex.simplecrudrest.model.Pet;

/**
 * Mapper for converting between Pet domain model and PetMongoDocument persistence model.
 * This separation allows the domain model to remain completely independent of
 * persistence framework concerns (JPA, MongoDB, etc.).
 */
public class PetDocumentMapper {

    private PetDocumentMapper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a Pet domain model to a PetMongoDocument for persistence
     * @param pet the domain model
     * @return the MongoDB document
     */
    public static PetMongoDocument toMongoDocument(Pet pet) {
        if (pet == null) {
            return null;
        }

        return PetMongoDocument.builder()
                .id(pet.getId())
                .name(pet.getName())
                .species(pet.getSpecies())
                .age(pet.getAge())
                .ownerName(pet.getOwnerName())
                .build();
    }

    /**
     * Converts a PetMongoDocument from persistence to a Pet domain model
     * @param document the MongoDB document
     * @return the domain model
     */
    public static Pet toDomainModel(PetMongoDocument document) {
        if (document == null) {
            return null;
        }

        return Pet.builder()
                .id(document.getId())
                .name(document.getName())
                .species(document.getSpecies())
                .age(document.getAge())
                .ownerName(document.getOwnerName())
                .build();
    }
}
package com.ex.simplecrudrest.service.mapper;

import com.ex.simplecrudrest.dto.PetRequest;
import com.ex.simplecrudrest.dto.PetResponse;
import com.ex.simplecrudrest.model.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Unit tests for PetMapper.
 * Tests all mapping logic between DTOs and domain models.
 */
@DisplayName("PetMapper Unit Tests")
class PetMapperTest {

    @Nested
    @DisplayName("fromDtoRequestToEntity() Tests")
    class FromDtoRequestToEntityTests {

        @Test
        @DisplayName("Should map all fields from PetRequest to Pet")
        void shouldMapAllFields() {
            // Given
            PetRequest request = PetRequest.builder()
                    .name("Buddy")
                    .species("Dog")
                    .age(3)
                    .ownerName("John Doe")
                    .build();

            // When
            Pet pet = PetMapper.fromDtoRequestToEntity(request);

            // Then
            assertThat(pet).isNotNull();
            assertThat(pet.getName()).isEqualTo("Buddy");
            assertThat(pet.getSpecies()).isEqualTo("Dog");
            assertThat(pet.getAge()).isEqualTo(3);
            assertThat(pet.getOwnerName()).isEqualTo("John Doe");
            assertThat(pet.getId()).isNull(); // ID should not be set from request
        }

        @Test
        @DisplayName("Should map only required fields when optional fields are null")
        void shouldMapOnlyRequiredFields() {
            // Given
            PetRequest request = PetRequest.builder()
                    .name("Whiskers")
                    .species("Cat")
                    .build();

            // When
            Pet pet = PetMapper.fromDtoRequestToEntity(request);

            // Then
            assertThat(pet).isNotNull();
            assertThat(pet.getName()).isEqualTo("Whiskers");
            assertThat(pet.getSpecies()).isEqualTo("Cat");
            assertThat(pet.getAge()).isNull();
            assertThat(pet.getOwnerName()).isNull();
            assertThat(pet.getId()).isNull();
        }

        @Test
        @DisplayName("Should handle empty strings in request")
        void shouldHandleEmptyStrings() {
            // Given
            PetRequest request = PetRequest.builder()
                    .name("")
                    .species("")
                    .ownerName("")
                    .build();

            // When
            Pet pet = PetMapper.fromDtoRequestToEntity(request);

            // Then
            assertThat(pet.getName()).isEmpty();
            assertThat(pet.getSpecies()).isEmpty();
            assertThat(pet.getOwnerName()).isEmpty();
        }


        @Test
        @DisplayName("Should handle special characters in strings")
        void shouldHandleSpecialCharacters() {
            // Given
            PetRequest request = PetRequest.builder()
                    .name("Mr. Whiskers O'Malley")
                    .species("Cat (Siamese)")
                    .ownerName("José García-Müller")
                    .build();

            // When
            Pet pet = PetMapper.fromDtoRequestToEntity(request);

            // Then
            assertThat(pet.getName()).isEqualTo("Mr. Whiskers O'Malley");
            assertThat(pet.getSpecies()).isEqualTo("Cat (Siamese)");
            assertThat(pet.getOwnerName()).isEqualTo("José García-Müller");
        }
    }

    @Nested
    @DisplayName("fromEntityToDtoResponse() Tests")
    class FromEntityToDtoResponseTests {

        @Test
        @DisplayName("Should map all fields from Pet to PetResponse")
        void shouldMapAllFields() {
            // Given
            Pet pet = Pet.builder()
                    .id(1L)
                    .name("Buddy")
                    .species("Dog")
                    .age(3)
                    .ownerName("John Doe")
                    .build();

            // When
            PetResponse response = PetMapper.fromEntityToDtoResponse(pet);

            // Then
            assertThat(response).isNotNull();
            assertThat(response.getId()).isEqualTo(1L);
            assertThat(response.getName()).isEqualTo("Buddy");
            assertThat(response.getSpecies()).isEqualTo("Dog");
            assertThat(response.getAge()).isEqualTo(3);
            assertThat(response.getOwnerName()).isEqualTo("John Doe");
        }

        @Test
        @DisplayName("Should map required fields when optional fields are null")
        void shouldMapRequiredFieldsOnly() {
            // Given
            Pet pet = Pet.builder()
                    .id(2L)
                    .name("Whiskers")
                    .species("Cat")
                    .build();

            // When
            PetResponse response = PetMapper.fromEntityToDtoResponse(pet);

            // Then
            assertThat(response).isNotNull();
            assertThat(response.getId()).isEqualTo(2L);
            assertThat(response.getName()).isEqualTo("Whiskers");
            assertThat(response.getSpecies()).isEqualTo("Cat");
            assertThat(response.getAge()).isNull();
            assertThat(response.getOwnerName()).isNull();
        }


    }

}
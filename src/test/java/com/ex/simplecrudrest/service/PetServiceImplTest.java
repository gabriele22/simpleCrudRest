package com.ex.simplecrudrest.service;

import com.ex.simplecrudrest.dto.PetRequest;
import com.ex.simplecrudrest.dto.PetResponse;
import com.ex.simplecrudrest.exception.PetNotFoundException;
import com.ex.simplecrudrest.model.Pet;
import com.ex.simplecrudrest.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pet Service Tests")
class PetServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetServiceImpl petService;

    private PetRequest petRequest;
    private Pet pet;

    @BeforeEach
    void setUp() {
        petRequest = PetRequest.builder()
                .name("Max")
                .species("Dog")
                .age(3)
                .ownerName("John Doe")
                .build();

        pet = Pet.builder()
                .id(1L)
                .name("Max")
                .species("Dog")
                .age(3)
                .ownerName("John Doe")
                .build();
    }

    @Test
    @DisplayName("Should create pet successfully")
    void shouldCreatePetSuccessfully() {
        // Given
        when(petRepository.save(any(Pet.class))).thenReturn(pet);

        // When
        PetResponse response = petService.createPet(petRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Max");
        assertThat(response.getSpecies()).isEqualTo("Dog");
        assertThat(response.getAge()).isEqualTo(3);
        assertThat(response.getOwnerName()).isEqualTo("John Doe");

        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    @DisplayName("Should get pet by id successfully")
    void shouldGetPetByIdSuccessfully() {
        // Given
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));

        // When
        PetResponse response = petService.getPetById(1L);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Max");

        verify(petRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw exception when pet not found by id")
    void shouldThrowExceptionWhenPetNotFoundById() {
        // Given
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> petService.getPetById(999L))
                .isInstanceOf(PetNotFoundException.class)
                .hasMessageContaining("Pet not found with id: 999");

        verify(petRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should get all pets successfully")
    void shouldGetAllPetsSuccessfully() {
        // Given
        Pet pet2 = Pet.builder()
                .id(2L)
                .name("Bella")
                .species("Cat")
                .age(2)
                .build();

        when(petRepository.findAll()).thenReturn(Arrays.asList(pet, pet2));

        // When
        List<PetResponse> responses = petService.getAllPets();

        // Then
        assertThat(responses).isNotNull();
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getName()).isEqualTo("Max");
        assertThat(responses.get(1).getName()).isEqualTo("Bella");

        verify(petRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should update pet successfully")
    void shouldUpdatePetSuccessfully() {
        // Given
        PetRequest updateRequest = PetRequest.builder()
                .name("Max Updated")
                .species("Dog")
                .age(4)
                .ownerName("Jane Doe")
                .build();

        Pet updatedPet = Pet.builder()
                .id(1L)
                .name("Max Updated")
                .species("Dog")
                .age(4)
                .ownerName("Jane Doe")
                .build();

        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(petRepository.save(any(Pet.class))).thenReturn(updatedPet);

        // When
        PetResponse response = petService.updatePet(1L, updateRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("Max Updated");
        assertThat(response.getAge()).isEqualTo(4);
        assertThat(response.getOwnerName()).isEqualTo("Jane Doe");

        verify(petRepository, times(1)).findById(1L);
        verify(petRepository, times(1)).save(any(Pet.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent pet")
    void shouldThrowExceptionWhenUpdatingNonExistentPet() {
        // Given
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> petService.updatePet(999L, petRequest))
                .isInstanceOf(PetNotFoundException.class)
                .hasMessageContaining("Pet not found with id: 999");

        verify(petRepository, times(1)).findById(999L);
        verify(petRepository, never()).save(any(Pet.class));
    }

    @Test
    @DisplayName("Should delete pet successfully")
    void shouldDeletePetSuccessfully() {
        // Given
        when(petRepository.existsById(1L)).thenReturn(true);
        when(petRepository.deleteById(1L)).thenReturn(true);

        // When
        petService.deletePet(1L);

        // Then
        verify(petRepository, times(1)).existsById(1L);
        verify(petRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent pet")
    void shouldThrowExceptionWhenDeletingNonExistentPet() {
        // Given
        when(petRepository.existsById(anyLong())).thenReturn(false);

        // When & Then
        assertThatThrownBy(() -> petService.deletePet(999L))
                .isInstanceOf(PetNotFoundException.class)
                .hasMessageContaining("Pet not found with id: 999");

        verify(petRepository, times(1)).existsById(999L);
        verify(petRepository, never()).deleteById(anyLong());
    }
}

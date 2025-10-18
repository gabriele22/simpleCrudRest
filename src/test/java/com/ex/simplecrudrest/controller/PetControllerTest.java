package com.ex.simplecrudrest.controller;

import com.ex.simplecrudrest.dto.PetRequest;
import com.ex.simplecrudrest.dto.PetResponse;
import com.ex.simplecrudrest.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PetController.class)
@DisplayName("Pet Controller Integration Tests")
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PetService petService;

    @Test
    @DisplayName("Should create pet and return 201 Created")
    void shouldCreatePetAndReturn201() throws Exception {
        // Given
        PetRequest request = PetRequest.builder()
                .name("Max")
                .species("Dog")
                .age(3)
                .ownerName("John Doe")
                .build();

        PetResponse response = PetResponse.builder()
                .id(1L)
                .name("Max")
                .species("Dog")
                .age(3)
                .ownerName("John Doe")
                .build();

        when(petService.createPet(any(PetRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Max")))
                .andExpect(jsonPath("$.species", is("Dog")))
                .andExpect(jsonPath("$.age", is(3)))
                .andExpect(jsonPath("$.ownerName", is("John Doe")));

        verify(petService, times(1)).createPet(any(PetRequest.class));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when name is missing")
    void shouldReturn400WhenNameIsMissing() throws Exception {
        // Given
        PetRequest request = PetRequest.builder()
                .species("Dog")
                .age(3)
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Validation Failed")))
                .andExpect(jsonPath("$.fieldErrors.name").exists());

        verify(petService, never()).createPet(any(PetRequest.class));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when species is missing")
    void shouldReturn400WhenSpeciesIsMissing() throws Exception {
        // Given
        PetRequest request = PetRequest.builder()
                .name("Max")
                .age(3)
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Validation Failed")))
                .andExpect(jsonPath("$.fieldErrors.species").exists());

        verify(petService, never()).createPet(any(PetRequest.class));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when age is negative")
    void shouldReturn400WhenAgeIsNegative() throws Exception {
        // Given
        PetRequest request = PetRequest.builder()
                .name("Max")
                .species("Dog")
                .age(-1)
                .build();

        // When & Then
        mockMvc.perform(post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Validation Failed")))
                .andExpect(jsonPath("$.fieldErrors.age").exists());

        verify(petService, never()).createPet(any(PetRequest.class));
    }

    @Test
    @DisplayName("Should get pet by id and return 200 OK")
    void shouldGetPetByIdAndReturn200() throws Exception {
        // Given
        PetResponse response = PetResponse.builder()
                .id(1L)
                .name("Max")
                .species("Dog")
                .age(3)
                .ownerName("John Doe")
                .build();

        when(petService.getPetById(1L)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/v1/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Max")))
                .andExpect(jsonPath("$.species", is("Dog")));

        verify(petService, times(1)).getPetById(1L);
    }

    @Test
    @DisplayName("Should get all pets and return 200 OK")
    void shouldGetAllPetsAndReturn200() throws Exception {
        // Given
        List<PetResponse> pets = Arrays.asList(
                PetResponse.builder().id(1L).name("Max").species("Dog").age(3).build(),
                PetResponse.builder().id(2L).name("Bella").species("Cat").age(2).build()
        );

        when(petService.getAllPets()).thenReturn(pets);

        // When & Then
        mockMvc.perform(get("/api/v1/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Max")))
                .andExpect(jsonPath("$[1].name", is("Bella")));

        verify(petService, times(1)).getAllPets();
    }

    @Test
    @DisplayName("Should update pet and return 200 OK")
    void shouldUpdatePetAndReturn200() throws Exception {
        // Given
        PetRequest request = PetRequest.builder()
                .name("Max Updated")
                .species("Dog")
                .age(4)
                .ownerName("Jane Doe")
                .build();

        PetResponse response = PetResponse.builder()
                .id(1L)
                .name("Max Updated")
                .species("Dog")
                .age(4)
                .ownerName("Jane Doe")
                .build();

        when(petService.updatePet(eq(1L), any(PetRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(put("/api/v1/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Max Updated")))
                .andExpect(jsonPath("$.age", is(4)))
                .andExpect(jsonPath("$.ownerName", is("Jane Doe")));

        verify(petService, times(1)).updatePet(eq(1L), any(PetRequest.class));
    }

    @Test
    @DisplayName("Should delete pet and return 204 No Content")
    void shouldDeletePetAndReturn204() throws Exception {
        // Given
        doNothing().when(petService).deletePet(1L);

        // When & Then
        mockMvc.perform(delete("/api/v1/pets/1"))
                .andExpect(status().isNoContent());

        verify(petService, times(1)).deletePet(1L);
    }
}
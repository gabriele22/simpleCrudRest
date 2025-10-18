package com.ex.simplecrudrest.service;

import com.ex.simplecrudrest.dto.PetRequest;
import com.ex.simplecrudrest.dto.PetResponse;
import com.ex.simplecrudrest.exception.PetNotFoundException;
import com.ex.simplecrudrest.service.mapper.PetMapper;
import com.ex.simplecrudrest.model.Pet;
import com.ex.simplecrudrest.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of PetService containing business logic for pet operations.
 * Uses constructor injection for better testability and immutability.
 */
@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;

    }

    @Override
    public PetResponse createPet(PetRequest request) {
        Pet pet = PetMapper.fromDtoRequestToEntity(request);
        Pet savedPet = petRepository.save(pet);
        return PetMapper.fromEntityToDtoResponse(savedPet);
    }

    @Override
    public PetResponse getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
        return PetMapper.fromEntityToDtoResponse(pet);
    }

    @Override
    public List<PetResponse> getAllPets() {
        return petRepository.findAll().stream()
                .map(PetMapper::fromEntityToDtoResponse)
                .toList();
    }

    @Override
    public PetResponse updatePet(Long id, PetRequest request) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));

        PetMapper.updateEntityFromRequest(existingPet, request);
        Pet updatedPet = petRepository.save(existingPet);

        return PetMapper.fromEntityToDtoResponse(updatedPet);
    }

    @Override
    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new PetNotFoundException(id);
        }
        petRepository.deleteById(id);
    }

    @Override
    public int getNumberOfDifferentSpecies() {
        return petRepository.getNumberOfDifferentSpecies();
    }

}
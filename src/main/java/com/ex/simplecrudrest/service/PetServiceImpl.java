package com.ex.simplecrudrest.service;

import com.ex.simplecrudrest.dto.PetRequest;
import com.ex.simplecrudrest.dto.PetResponse;
import com.ex.simplecrudrest.exception.PetNotFoundException;
import com.ex.simplecrudrest.service.mapper.PetMapper;
import com.ex.simplecrudrest.model.Pet;
import com.ex.simplecrudrest.dao.PetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of PetService containing business logic for pet operations.
 * Uses constructor injection for better testability and immutability.
 */
@Service
public class PetServiceImpl implements PetService {

    private final PetDao petDao;

    @Autowired
    public PetServiceImpl(PetDao petDao) {
        this.petDao = petDao;

    }

    @Override
    public PetResponse createPet(PetRequest request) {
        Pet pet = PetMapper.fromDtoRequestToEntity(request);
        Pet savedPet = petDao.save(pet);
        return PetMapper.fromEntityToDtoResponse(savedPet);
    }

    @Override
    public PetResponse getPetById(Long id) {
        Pet pet = petDao.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));
        return PetMapper.fromEntityToDtoResponse(pet);
    }

    @Override
    public List<PetResponse> getAllPets() {
        return petDao.findAll().stream()
                .map(PetMapper::fromEntityToDtoResponse)
                .toList();
    }

    @Override
    public PetResponse updatePet(Long id, PetRequest request) {
        Pet existingPet = petDao.findById(id)
                .orElseThrow(() -> new PetNotFoundException(id));

        PetMapper.updateEntityFromRequest(existingPet, request);
        Pet updatedPet = petDao.save(existingPet);

        return PetMapper.fromEntityToDtoResponse(updatedPet);
    }

    @Override
    public void deletePet(Long id) {
        if (!petDao.existsById(id)) {
            throw new PetNotFoundException(id);
        }
        petDao.deleteById(id);
    }

    @Override
    public int getNumberOfDifferentSpecies() {
        return petDao.getNumberOfDifferentSpecies();
    }

}
package com.ex.simplecrudrest.repository;

import com.ex.simplecrudrest.model.Pet;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * In-memory implementation of PetRepository.
 * This implementation simulates a relational database using a ConcurrentHashMap
 * for thread-safe operations. It can be easily replaced with a JPA or NoSQL
 * implementation without changing the service layer.
 */
@Repository
@Profile("in-memory")
public class InMemoryPetRepository implements PetRepository {

    private final Map<Long, Pet> database = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            // New pet - generate ID
            pet.setId(idGenerator.getAndIncrement());
        }
        database.put(pet.getId(), pet);
        return pet;
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Pet> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public void deleteById(Long id) {
        database.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return database.containsKey(id);
    }

    @Override
    public int getNumberOfDifferentSpecies() {
        return database.values().stream()
                .map(Pet::getSpecies)
                .collect(Collectors.toSet())
                .size();
    }
}
package com.ex.simplecrudrest.dao;

import com.ex.simplecrudrest.dao.entity.PetJpaEntity;
import com.ex.simplecrudrest.dao.mapper.PetEntityMapper;
import com.ex.simplecrudrest.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * JPA implementation of PetDao interface.
 * This class acts as an adapter between the domain's PetDao interface and Spring Data JPA's
 * PetJpaRepository. It handles the conversion between Pet domain models and PetJpaEntity
 * persistence models, keeping JPA concerns isolated from the domain layer.
 */
@Repository
@Profile("jpa")
public class JpaPetDaoImpl implements PetDao {

    private final JpaPetRepository repository;

    @Autowired
    public JpaPetDaoImpl(JpaPetRepository repository) {
        this.repository = repository;
    }

    @Override
    public Pet save(Pet pet) {
        PetJpaEntity entity = PetEntityMapper.toJpaEntity(pet);
        PetJpaEntity savedEntity = repository.save(entity);
        return PetEntityMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return repository.findById(id)
                .map(PetEntityMapper::toDomainModel);
    }

    @Override
    public List<Pet> findAll() {
        return repository.findAll().stream()
                .map(PetEntityMapper::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public int getNumberOfDifferentSpecies() {
        return repository.countDistinctSpecies();
    }
}

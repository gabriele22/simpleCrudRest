package com.ex.simplecrudrest.dao;

import com.ex.simplecrudrest.dao.entity.PetJpaEntity;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository interface for PetJpaEntity.
 * This interface extends JpaRepository to leverage Spring Data JPA's
 * built-in CRUD operations. It's kept internal to the DAO layer and
 * wrapped by JpaPetDaoImpl which implements the domain's PetDao interface.
 */
@Repository
@Profile("jpa")
interface JpaPetRepository extends JpaRepository<PetJpaEntity, Long> {

    /**
     * Calculates the number of unique species.
     * The query is executed directly by the database.
     */
    @Query("SELECT COUNT(DISTINCT p.species) FROM PetJpaEntity p")
    int countDistinctSpecies();
}
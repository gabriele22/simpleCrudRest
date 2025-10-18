package com.ex.simplecrudrest.repository;

import com.ex.simplecrudrest.model.Pet;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface JpaPetRepository extends JpaRepository<Pet, Long>, PetRepository {
    /**
     * Calculates the number of unique species.
     * The query is executed directly by the database.
     */
    @Override
    @Query("SELECT COUNT(DISTINCT p.species) FROM Pet p")
    public int getNumberOfDifferentSpecies();
}
package com.ex.simplecrudrest.repository;

import com.ex.simplecrudrest.model.Pet;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface JpaPetRepository extends JpaRepository<Pet, Long>, PetRepository {



}
package com.ex.simplecrudrest.dao;

import com.ex.simplecrudrest.dao.document.PetMongoDocument;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * MongoDB Repository interface for PetMongoDocument.
 * This interface extends MongoRepository to leverage Spring Data MongoDB's
 * built-in CRUD operations. It's kept internal to the DAO layer and
 * wrapped by MongoPetDaoImpl which implements the domain's PetDao interface.
 */
@Repository
@Profile("mongodb")
interface MongoPetRepository extends MongoRepository<PetMongoDocument, Long> {

    /**
     * Calculates the number of unique species using MongoDB aggregation.
     * Uses aggregation framework to count distinct species efficiently.
     */
    @Aggregation(pipeline = {
        "{ '$group': { '_id': '$species' } }",
        "{ '$count': 'total' }"
    })
    Integer countDistinctSpecies();
}
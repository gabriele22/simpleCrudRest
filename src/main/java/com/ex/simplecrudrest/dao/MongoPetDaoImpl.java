package com.ex.simplecrudrest.dao;

import com.ex.simplecrudrest.dao.document.PetMongoDocument;
import com.ex.simplecrudrest.dao.mapper.PetDocumentMapper;
import com.ex.simplecrudrest.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * MongoDB implementation of PetDao interface.
 * This class acts as an adapter between the domain's PetDao interface and Spring Data MongoDB's
 * MongoPetRepository. It handles the conversion between Pet domain models and PetMongoDocument
 * persistence models, keeping MongoDB concerns isolated from the domain layer.
 */
@Repository
@Profile("mongodb")
public class MongoPetDaoImpl implements PetDao {

    private final MongoPetRepository repository;
    private final MongoTemplate mongoTemplate;
    private final AtomicLong idGenerator;

    @Autowired
    public MongoPetDaoImpl(MongoPetRepository repository, MongoTemplate mongoTemplate) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
        // Initialize ID generator with the current max ID + 1
        this.idGenerator = new AtomicLong(getMaxId() + 1);
    }

    /**
     * Gets the maximum ID currently in the database
     */
    private long getMaxId() {
        Query query = new Query();
        query.with(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "id"));
        query.limit(1);
        PetMongoDocument maxDoc = mongoTemplate.findOne(query, PetMongoDocument.class);
        return maxDoc != null && maxDoc.getId() != null ? maxDoc.getId() : 0;
    }

    @Override
    public Pet save(Pet pet) {
        if (pet.getId() == null) {
            // New pet - generate ID
            pet.setId(idGenerator.getAndIncrement());
        }
        PetMongoDocument document = PetDocumentMapper.toMongoDocument(pet);
        PetMongoDocument savedDocument = repository.save(document);
        return PetDocumentMapper.toDomainModel(savedDocument);
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return repository.findById(id)
                .map(PetDocumentMapper::toDomainModel);
    }

    @Override
    public List<Pet> findAll() {
        return repository.findAll().stream()
                .map(PetDocumentMapper::toDomainModel)
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
        // Use aggregation to count distinct species
        Query query = new Query();
        List<String> distinctSpecies = mongoTemplate.findDistinct(query, "species", PetMongoDocument.class, String.class);
        return distinctSpecies.size();
    }
}
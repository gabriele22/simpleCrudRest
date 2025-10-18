package com.ex.simplecrudrest.dao.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * MongoDB Document representation of a Pet for non-relational database persistence.
 * This class is specific to MongoDB implementation and should only be used
 * within the MongoPetDao implementation. The domain layer uses the pure Pet domain model.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pets")
public class PetMongoDocument {

    /**
     * Unique identifier for the pet (MongoDB auto-generated ObjectId converted to Long)
     * MongoDB typically uses ObjectId, but we use Long to maintain consistency with the domain model
     */
    @Id
    private Long id;

    /**
     * Name of the pet (required)
     */
    @Field("name")
    private String name;

    /**
     * Species of the pet (e.g., "Dog", "Cat", "Rabbit") (required)
     */
    @Field("species")
    private String species;

    /**
     * Age of the pet in years (optional, must be >= 0 if provided)
     */
    @Field("age")
    private Integer age;

    /**
     * Name of the pet's owner (optional)
     */
    @Field("owner_name")
    private String ownerName;
}
package com.ex.simplecrudrest.exception;

/**
 * Exception thrown when a pet with the specified ID is not found.
 */
public class PetNotFoundException extends RuntimeException {

    public PetNotFoundException(Long id) {
        super("Pet not found with id: " + id);
    }
}
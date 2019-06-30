package com.vektor.pivot.exceptions;

/**
 * Custom {@link RuntimeException} to be thrown when invalid input is provided.
 */
public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(String.format("Invalid input: %s.", message));
    }
}

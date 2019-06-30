package com.vektor.pivot.exceptions;

/**
 * Custom {@link RuntimeException} to be thrown when an invalid separator is provided.
 */
public class InvalidSeparatorException extends RuntimeException {
    public InvalidSeparatorException(String message) {
        super(message);
    }
}

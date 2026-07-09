package com.example.demo.exception;

// thrown when something the user asked for does not make sense
// example: not enough stock, shelter already full, etc.
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}

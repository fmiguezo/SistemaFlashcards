package edu.utn.infrastructure.adapters.out.exception;

import java.util.UUID;

public class DeckNoExisteException extends RuntimeException {
    public DeckNoExisteException(String message) {
        super(message);
    }
}

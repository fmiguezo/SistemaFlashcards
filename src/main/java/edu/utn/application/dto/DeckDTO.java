package edu.utn.application.dto;

import edu.utn.domain.model.flashcard.IFlashcard;

import java.util.List;
import java.util.UUID;

public class DeckDTO {
    private UUID id;
    private String nombre;
    private String descripcion;
    private List<IFlashcard> flashcards;

    public DeckDTO(UUID id, String nombre, String descripcion, List<IFlashcard> flashcards) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.flashcards = flashcards;
    }

    public UUID getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<IFlashcard> getFlashcards() {
        return flashcards;
    }
}

package edu.utn.infrastructure.adapters.in.rest.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class DeckResponse {
    private UUID id;
    private String nombre;
    private String descripcion;
    private List<FlashcardResponse> flashcards;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DeckResponse(UUID id, String nombre, String descripcion, List<FlashcardResponse> flashcards) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.flashcards = flashcards;
    }

    // Getters y setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public List<FlashcardResponse> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<FlashcardResponse> flashcards) {
        this.flashcards = flashcards;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

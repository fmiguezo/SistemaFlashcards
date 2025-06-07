package edu.utn.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IDeck {
    UUID getId();
    String getNombre();
    String getDescripcion();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    void setNombre(String nombre);
    void setDescripcion(String descripcion);
    void setUpdatedAt(LocalDateTime updatedAt);
    List<IFlashcard> getFlashcards();
    void addFlashcard(IFlashcard flashcard);
}

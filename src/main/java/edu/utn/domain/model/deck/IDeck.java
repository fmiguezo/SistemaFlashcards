package edu.utn.domain.model.deck;

import edu.utn.domain.model.flashcard.IFlashcard;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IDeck {
    void setId(UUID id);
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

    void setCreatedAt(LocalDateTime createdAt);

    void setFlashcards(List<IFlashcard> flashcards);
}

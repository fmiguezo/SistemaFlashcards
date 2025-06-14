package edu.utn.domain.model.flashcard;
import edu.utn.domain.model.deck.IDeck;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IFlashcard {
    void setId(UUID id);
    UUID getId();
    String getPregunta();
    String getRespuesta();
    void setPregunta(String pregunta);
    void setRespuesta(String respuesta);
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    void setUpdatedAt(LocalDateTime updatedAt);
    LocalDateTime getNextReviewDate();
    void setNextReviewDate(LocalDateTime nextReviewDate);
    LocalDateTime getLastReviewDate();
    void setLastReviewDate(LocalDateTime lastReviewDate);
    int getScore();
    void setScore(int score);

    void setCreatedAt(LocalDateTime createdAt);

    IDeck getDeck();

    void setDeck(IDeck deck);
}

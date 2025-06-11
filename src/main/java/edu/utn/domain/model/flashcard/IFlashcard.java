package edu.utn.domain.model.flashcard;
import java.time.LocalDateTime;
import java.util.UUID;

public interface IFlashcard {
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
}

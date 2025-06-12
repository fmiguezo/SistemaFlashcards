package edu.utn.domain.model.flashcard;
import edu.utn.domain.model.deck.Deck;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "flashcard")
public class Flashcard implements IFlashcard {

    @Id
    @GeneratedValue
    private UUID id;
    private String pregunta;
    private String respuesta;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime nextReviewDate;
    private LocalDateTime lastReviewDate;
    private int score;

    // Constructor vacío requerido por JPA
    protected Flashcard() {}

    public Flashcard(String pregunta, String respuesta) {
        this.id = UUID.randomUUID();
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.createdAt = LocalDateTime.now();
        this.nextReviewDate = this.createdAt;
        this.updatedAt = this.createdAt;
        this.lastReviewDate = null;
        this.score = 0;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getPregunta() {
        return pregunta;
    }

    @Override
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    @Override
    public String getRespuesta() {
        return respuesta;
    }

    @Override
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public LocalDateTime getNextReviewDate() {
        return nextReviewDate;
    }

    @Override
    public void setNextReviewDate(LocalDateTime nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }

    @Override
    public LocalDateTime getLastReviewDate() {
        return lastReviewDate;
    }

    @Override
    public void setLastReviewDate(LocalDateTime lastReviewDate) {
        this.lastReviewDate = lastReviewDate;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }
}

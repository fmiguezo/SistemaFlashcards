package edu.utn.infrastructure.adapters.out.persistence.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "flashcard")
public class FlashcardEntity {

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
;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false)
    @JsonBackReference
    private DeckEntity deck;

    protected FlashcardEntity() {}

    public FlashcardEntity(String pregunta, String respuesta) {
        this.id = UUID.randomUUID();
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.createdAt = LocalDateTime.now();
        this.nextReviewDate = this.createdAt;
        this.updatedAt = this.createdAt;
        this.lastReviewDate = null;
        this.score = 0;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
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

    public LocalDateTime getNextReviewDate() {
        return nextReviewDate;
    }

    public void setNextReviewDate(LocalDateTime nextReviewDate) {
        this.nextReviewDate = nextReviewDate;
    }

    public LocalDateTime getLastReviewDate() {
        return lastReviewDate;
    }

    public void setLastReviewDate(LocalDateTime lastReviewDate) {
        this.lastReviewDate = lastReviewDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public DeckEntity getDeck() {
        return deck;
    }

    public void setDeck(DeckEntity deck) {
        this.deck = deck;
    }
}
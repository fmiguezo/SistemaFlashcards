package edu.utn.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class FlashcardDTO {
    private UUID id;
    private String pregunta;
    private String respuesta;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime nextReviewDate;
    private LocalDateTime lastReviewDate;
    private int score;
    private UUID deckID;

    public FlashcardDTO(String pregunta, String respuesta, UUID deckID) {
        this.id = null;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
        this.nextReviewDate = LocalDateTime.now();
        this.lastReviewDate = null;
        this.score = 0;
        this.deckID = deckID;
    }

    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    @JsonProperty("question")
    public String getPregunta() {
        return pregunta;
    }

    @JsonProperty("question")
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    @JsonProperty("answer")
    public String getRespuesta() {
        return respuesta;
    }

    @JsonProperty("answer")
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

    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public UUID getDeckID() {
        return deckID;
    }
    public void setDeckID(UUID deckID) {
        this.deckID = deckID;
    }
}

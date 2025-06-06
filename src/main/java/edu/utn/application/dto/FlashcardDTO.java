package edu.utn.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class FlashcardDTO {
    private UUID id;
    private String pregunta;
    private String respuesta;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime nextReviewDate;

    public FlashcardDTO(UUID id, String pregunta, String respuesta, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime nextReviewDate) {
        this.id = id;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.nextReviewDate = nextReviewDate;
    }

    public UUID getId() {
        return id;
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
}

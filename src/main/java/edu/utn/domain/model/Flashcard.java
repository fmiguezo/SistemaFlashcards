package edu.utn.domain.model;
import java.time.LocalDateTime;
import java.util.UUID;

public class Flashcard implements IFlashcard{
    private UUID id;
    private String pregunta;
    private String respuesta;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime nextReviewDate;

    public Flashcard(String pregunta, String respuesta) {
        this.id = UUID.randomUUID();
        this.pregunta = pregunta;
        this.respuesta = respuesta;
        this.createdAt = LocalDateTime.now();
        this.nextReviewDate = this.createdAt;
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
    public void setNextReviewDate(IEstrategiaRepeticion estrategia, int puntuacion) {
        this.nextReviewDate = estrategia.calcularProximaRepeticion(puntuacion, LocalDateTime.now());
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public LocalDateTime getNextReviewDate() {
        return nextReviewDate;
    }
}

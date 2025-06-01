package edu.utn.domain.model;
import java.time.LocalDateTime;
import java.util.UUID;

public class Flashcard {
    private UUID id;
    private String front;
    private String back;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String estrategiaDeRepeticion; //Esto vamos a tener que cambiarlo por una interfaz de estrategia de repeticion
    private LocalDateTime fechaDeUltimaRevision;
    private String deckId;

    public Flashcard(){};

    public Flashcard(UUID id, String front, String back) {
        this.id = id;
        this.front = front;
        this.back = back;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getFront() {
        return front;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
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

    public String getEstrategiaDeRepeticion() {
        return estrategiaDeRepeticion;
    }

    public void setEstrategiaDeRepeticion(String estrategiaDeRepeticion) {
        this.estrategiaDeRepeticion = estrategiaDeRepeticion;
    }

    public LocalDateTime getFechaDeUltimaRevision() {
        return fechaDeUltimaRevision;
    }

    public void setFechaDeUltimaRevision(LocalDateTime fechaDeUltimaRevision) {
        this.fechaDeUltimaRevision = fechaDeUltimaRevision;
    }

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }
}

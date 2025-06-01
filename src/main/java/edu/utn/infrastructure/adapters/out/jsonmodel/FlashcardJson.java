package edu.utn.infrastructure.adapters.out.jsonmodel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlashcardJson {

    private UUID id;
    private String front;
    private String back;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String estrategiaDeRepeticion; //Esto vamos a tener que cambiarlo por una interfaz de estrategia de repeticion
    private LocalDateTime fechaDeUltimaRevision;
    private String deckId;

    public FlashcardJson(UUID id, String front, String back, LocalDateTime createdAt, LocalDateTime updatedAt, String estrategiaDeRepeticion, LocalDateTime fechaDeUltimaRevision, String deckId) {
        this.id = id;
        this.front = front;
        this.back = back;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.estrategiaDeRepeticion = estrategiaDeRepeticion;
        this.fechaDeUltimaRevision = fechaDeUltimaRevision;
        this.deckId = deckId;
    }

    public FlashcardJson(UUID id, String front, String back) {
        this.id = id;
        this.front = front;
        this.back = back;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.estrategiaDeRepeticion = "default"; // Valor por defecto, se puede cambiar según la lógica de negocio
        this.fechaDeUltimaRevision = LocalDateTime.now(); // Inicialmente, la fecha de última revisión es ahora
        this.deckId = null; // Inicialmente, no pertenece a ningún deck
    }

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    public void setFechaDeUltimaRevision(LocalDateTime fechaDeUltimaRevision) {
        this.fechaDeUltimaRevision = fechaDeUltimaRevision;
    }

    public void setEstrategiaDeRepeticion(String estrategiaDeRepeticion) {
        this.estrategiaDeRepeticion = estrategiaDeRepeticion;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public UUID getId() {
        return id;
    }

    public String getFront() {
        return front;
    }

    public String getBack() {
        return back;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getEstrategiaDeRepeticion() {
        return estrategiaDeRepeticion;
    }

    public LocalDateTime getFechaDeUltimaRevision() {
        return fechaDeUltimaRevision;
    }
}

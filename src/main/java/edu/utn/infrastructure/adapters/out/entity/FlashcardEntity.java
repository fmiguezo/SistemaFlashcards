package edu.utn.infrastructure.adapters.out.entity;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.*;


@Entity
@Table(name = "flashcards")
public class FlashcardEntity {

    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "deck_id") // nombre de la columna en la tabla flashcards
    private DeckEntity deck;
    private String front;
    private String back;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String estrategiaDeRepeticion; //Esto vamos a tener que cambiarlo por una interfaz de estrategia de repeticion
    private LocalDateTime fechaDeUltimaRevision;



    public FlashcardEntity() {};

    public FlashcardEntity(UUID id, String front, String back, DeckEntity deck) {
        this.id = id;
        this.front = front;
        this.back = back;
        this.deck = deck;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.estrategiaDeRepeticion = "default";
        this.fechaDeUltimaRevision = LocalDateTime.now();
    }

    public DeckEntity getDeck() {
        return deck;
    }

    public void setDeck(DeckEntity deck) {
        this.deck = deck;
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

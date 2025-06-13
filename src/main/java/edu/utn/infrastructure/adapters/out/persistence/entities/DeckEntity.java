package edu.utn.infrastructure.adapters.out.persistence.entities;

import edu.utn.domain.model.deck.IDeck;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "deck")
public class DeckEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String nombre;
    private String descripcion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlashcardEntity> flashcards = new ArrayList<>();

    protected DeckEntity() {}

    public DeckEntity(String nombre, String descripcion) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    @Transactional
    @OneToMany(mappedBy = "deck", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    public List<FlashcardEntity> getFlashcards() {
        return flashcards;
    }
    public void setFlashcards(List<FlashcardEntity> flashcards) {
        this.flashcards = flashcards;
    }
}
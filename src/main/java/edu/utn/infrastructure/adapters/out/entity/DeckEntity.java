package edu.utn.infrastructure.adapters.out.entity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;


@Entity
@Table(name = "decks")
public class DeckEntity {

    @Id
    private UUID id;
    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlashcardEntity> flashcards;
    private String nombre;
    private String descripcion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public DeckEntity() {}

    public DeckEntity(UUID id, String nombre, String descripcion, List<FlashcardEntity> flashcards) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.flashcards = flashcards;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setFlashcards(List<FlashcardEntity> flashcards) {
        this.flashcards = flashcards;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<FlashcardEntity> getFlashcards() {
        return flashcards;
    }
}

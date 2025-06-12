package edu.utn.domain.model.deck;
import edu.utn.domain.model.flashcard.IFlashcard;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "deck")
public class Deck implements IDeck{

    @Id
    @GeneratedValue
    private UUID id;
    private String nombre;
    private String descripcion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "deck_id") // esto genera el campo deck_id en la tabla flashcard
    private List<IFlashcard> flashcards;

    // El constructor sin ID es necesario para el JPA
    protected Deck() {}

    public Deck( String nombre, String descripcion) {
        this.id = UUID.randomUUID();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.flashcards = new ArrayList<IFlashcard>();
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public List<IFlashcard> getFlashcards() {
        return flashcards;
    }

    @Override
    public void addFlashcard(IFlashcard flashcard) {
        flashcards.add(flashcard);
    }
}

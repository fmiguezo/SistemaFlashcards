package edu.utn.infrastructure.adapters.out.jsonmodel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeckJson {

    private UUID id;
    private String nombre;
    private String descripcion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<FlashcardJson> flashcards;

    public DeckJson() {}

    public DeckJson(UUID id, String nombre, String descripcion, List<FlashcardJson> flashcards) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.flashcards = flashcards;
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

    public void setFlashcards(List<FlashcardJson> flashcards) {
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

    public List<FlashcardJson> getFlashcards() {
        return flashcards;
    }
}

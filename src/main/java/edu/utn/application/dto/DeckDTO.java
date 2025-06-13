package edu.utn.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DeckDTO {
    private UUID id;
    private String nombre;
    private String descripcion;
    private List<FlashcardDTO> flashcards;

    public DeckDTO(String nombre, String descripcion) {
        this.id = null;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.flashcards = new ArrayList<>();
    }

    @JsonProperty("id")

    public UUID getId() {
        return id;
    }
    @JsonProperty("name")
    public String getNombre() {
        return nombre;
    }

    @JsonProperty("name")
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @JsonProperty("description")
    public String getDescripcion() {
        return descripcion;
    }

    @JsonProperty("description")
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @JsonProperty("flashcards")
    public List<FlashcardDTO> getFlashcards() {
        return flashcards;
    }

    public void addFlashcard(FlashcardDTO newFlashcard) {
        if (newFlashcard != null && !flashcards.contains(newFlashcard)) {
            flashcards.add(newFlashcard);
        }
    }
    @JsonProperty("id")
    public void setId(UUID uuid) {
        this.id = uuid;
    }

    public void setFlashcards(List<FlashcardDTO> flashcards) {
        this.flashcards = flashcards;
    }
}

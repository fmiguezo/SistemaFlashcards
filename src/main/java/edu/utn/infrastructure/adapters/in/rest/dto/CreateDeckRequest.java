package edu.utn.infrastructure.adapters.in.rest.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CreateDeckRequest {
    public String nombre;
    public String descripcion;
    public List<CreateFlashcardRequest> flashcards;

    public CreateDeckRequest(String nombre, String descripcion, List<CreateFlashcardRequest> flashcards) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.flashcards = flashcards;
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
    public List<CreateFlashcardRequest> getFlashcards() {
        return flashcards;
    }
    @JsonProperty("flashcards")
    public void setFlashcards(List<CreateFlashcardRequest> flashcards) {
        this.flashcards = flashcards;
    }
}

package edu.utn.infrastructure.adapters.in.rest.dto;
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

    public List<CreateFlashcardRequest> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<CreateFlashcardRequest> flashcards) {
        this.flashcards = flashcards;
    }
}

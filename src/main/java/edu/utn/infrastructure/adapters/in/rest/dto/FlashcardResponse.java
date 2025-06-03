package edu.utn.infrastructure.adapters.in.rest.dto;

import java.util.UUID;

public class FlashcardResponse {
    public UUID id;
    public String pregunta;
    public String respuesta;

    public FlashcardResponse(UUID id, String pregunta, String respuesta) {
        this.id = id;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}

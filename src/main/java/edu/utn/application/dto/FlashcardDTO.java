package edu.utn.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class FlashcardDTO {
    private UUID id;
    private String pregunta;
    private String respuesta;
    private LocalDateTime fechaDeUltimaRevision;

    public FlashcardDTO(UUID id, String pregunta, String respuesta) {
        this.id = id;
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }

    public UUID getId() {
        return id;
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

    public LocalDateTime getFechaDeUltimaRevision() {
        return fechaDeUltimaRevision;
    }

    public void setFechaDeUltimaRevision(LocalDateTime fechaDeUltimaRevision) {
        this.fechaDeUltimaRevision = fechaDeUltimaRevision;
    }
}

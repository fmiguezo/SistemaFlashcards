package edu.utn.domain.model;
import java.time.LocalDateTime;
import java.util.UUID;

public interface IFlashcard {
    UUID getId();
    String getPregunta();
    String getRespuesta();
    void setPregunta(String pregunta);
    void setRespuesta(String respuesta);
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    void setUpdatedAt(LocalDateTime updatedAt);
    LocalDateTime getFechaDeUltimaRevision();
    void setFechaDeUltimaRevision(LocalDateTime fechaDeUltimaRevision);
}

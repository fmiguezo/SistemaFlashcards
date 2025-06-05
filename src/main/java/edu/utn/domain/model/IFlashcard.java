package edu.utn.domain.model;
import java.util.UUID;

public interface IFlashcard {
    UUID getId();
    String getPregunta();
    String getRespuesta();
    void setPregunta(String pregunta);
    void setRespuesta(String respuesta);
}

package edu.utn.infrastructure.adapters.in.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateFlashcardRequest {
    public String pregunta;
    public String respuesta;

    public CreateFlashcardRequest(String pregunta, String respuesta) {
        this.pregunta = pregunta;
        this.respuesta = respuesta;
    }
    @JsonProperty("question")
    public String getPregunta() {
        return pregunta;
    }
    @JsonProperty("question")
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
    @JsonProperty("answer")
    public String getRespuesta() {
        return respuesta;
    }
    @JsonProperty("answer")
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
}

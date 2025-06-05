package edu.utn.domain.model;

import java.time.LocalDateTime;

public interface IEstrategiaRepeticion {
    LocalDateTime calcularProximaRepeticion(int puntuacion, LocalDateTime actual);
}

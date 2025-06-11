package edu.utn.domain.model.estrategia;

import java.time.LocalDateTime;

public interface IEstrategiaRepeticion {
    LocalDateTime calcularProximaRepeticion(int puntuacion, LocalDateTime actual);
}

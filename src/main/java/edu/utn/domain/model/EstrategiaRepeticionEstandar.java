package edu.utn.domain.model;

import java.time.LocalDateTime;

public class EstrategiaRepeticionEstandar implements IEstrategiaRepeticion {
    @Override
    public LocalDateTime calcularProximaRepeticion(int puntuacion, LocalDateTime actual) {
        switch (puntuacion) {
            case 5: return actual.plusDays(7);
            case 4: return actual.plusDays(3);
            case 3: return actual.plusDays(1);
            case 2: return actual.plusHours(12);
            case 1: return actual.plusHours(6);
            default: return actual.plusHours(1);
        }
    }
}


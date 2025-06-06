package edu.utn.domain.model;

import java.time.LocalDateTime;

public class EstrategiaRepeticionEstandar implements IEstrategiaRepeticion {
    @Override
    public LocalDateTime calcularProximaRepeticion(int puntuacion, LocalDateTime ahora) {
        switch (puntuacion) {
            case 1: return ahora.plusDays(1);
            case 2: return ahora.plusDays(2);
            case 3: return ahora.plusDays(4);
            case 4: return ahora.plusDays(7);
            case 5: return ahora.plusDays(15);
            default: return ahora.plusHours(1);
        }
    }
}


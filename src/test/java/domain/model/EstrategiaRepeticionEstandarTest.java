package domain.model;

import edu.utn.domain.model.EstrategiaRepeticionEstandar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EstrategiaRepeticionEstandarTest {

    private EstrategiaRepeticionEstandar estrategia;
    private LocalDateTime ahora;

    @BeforeEach
    void setUp() {
        estrategia = new EstrategiaRepeticionEstandar();
        ahora = LocalDateTime.of(2025, 6, 4, 12, 0);
    }

    @Test
    void testPuntuacion5() {
        LocalDateTime resultado = estrategia.calcularProximaRepeticion(5, ahora);
        assertEquals(ahora.plusDays(7), resultado);
    }

    @Test
    void testPuntuacion4() {
        LocalDateTime resultado = estrategia.calcularProximaRepeticion(4, ahora);
        assertEquals(ahora.plusDays(3), resultado);
    }

    @Test
    void testPuntuacion3() {
        LocalDateTime resultado = estrategia.calcularProximaRepeticion(3, ahora);
        assertEquals(ahora.plusDays(1), resultado);
    }

    @Test
    void testPuntuacion2() {
        LocalDateTime resultado = estrategia.calcularProximaRepeticion(2, ahora);
        assertEquals(ahora.plusHours(12), resultado);
    }

    @Test
    void testPuntuacion1() {
        LocalDateTime resultado = estrategia.calcularProximaRepeticion(1, ahora);
        assertEquals(ahora.plusHours(6), resultado);
    }

    @Test
    void testPuntuacionInvalida() {
        LocalDateTime resultado = estrategia.calcularProximaRepeticion(0, ahora);
        assertEquals(ahora.plusHours(1), resultado);
    }
}
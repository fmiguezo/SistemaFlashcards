package domain.model;

import edu.utn.domain.model.EstrategiaRepeticionEstandar;
import edu.utn.domain.model.Flashcard;
import edu.utn.domain.model.IEstrategiaRepeticion;
import edu.utn.domain.model.IFlashcard;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardTest {

    @Test
    void testFlashcardCreacionCorrecta() {
        String pregunta = "Pregunta";
        String respuesta = "Respuesta";

        IFlashcard flashcard = new Flashcard(pregunta, respuesta);

        assertNotNull(flashcard.getId());
        assertEquals(pregunta, flashcard.getPregunta());
        assertEquals(respuesta, flashcard.getRespuesta());
        assertNotNull(flashcard.getCreatedAt());
        assertNull(flashcard.getUpdatedAt());
    }

    @Test
    void testSetPregunta() {
        IFlashcard flashcard = new Flashcard("Pregunta", "Respuesta");

        flashcard.setPregunta("Nueva pregunta");

        assertEquals("Nueva pregunta", flashcard.getPregunta());
    }

    @Test
    void testSetRespuesta() {
        IFlashcard flashcard = new Flashcard("Pregunta", "Respuesta");

        flashcard.setRespuesta("Nueva respuesta");

        assertEquals("Nueva respuesta", flashcard.getRespuesta());
    }

    @Test
    void testSetUpdatedAt() {
        IFlashcard flashcard = new Flashcard("Pregunta", "Respuesta");
        LocalDateTime now = LocalDateTime.now();

        flashcard.setUpdatedAt(now);

        assertEquals(now, flashcard.getUpdatedAt());
    }

    @Test
    void testIdUnico() {
        IFlashcard f1 = new Flashcard("P1", "R1");
        IFlashcard f2 = new Flashcard("P2", "R2");

        assertNotEquals(f1.getId(), f2.getId());
    }

    @Test
    void setNextReviewDate_deberiaActualizarCorrectamente() {
        IFlashcard flashcard = new Flashcard("Pregunta", "Respuesta");
        IEstrategiaRepeticion estrategia = new EstrategiaRepeticionEstandar();

        flashcard.setNextReviewDate(estrategia, 5);
        LocalDateTime esperadoMin = LocalDateTime.now().plusDays(7).minusSeconds(5);
        LocalDateTime esperadoMax = LocalDateTime.now().plusDays(7).plusSeconds(5);

        assertNotNull(flashcard.getNextReviewDate());
        assertTrue(flashcard.getNextReviewDate().isAfter(esperadoMin));
        assertTrue(flashcard.getNextReviewDate().isBefore(esperadoMax));
    }
}

package domain.model;

import edu.utn.domain.model.deck.Deck;
import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.model.flashcard.IFlashcard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardTest {
    private Deck testDeck;

    @BeforeEach
    void setup() {
        testDeck = new Deck();
        testDeck.setNombre("Deck de prueba");
        testDeck.setDescripcion("Descripción del deck de prueba");
    }


    @Test
    void testFlashcardCreacionCorrecta() {
        String pregunta = "Pregunta";
        String respuesta = "Respuesta";

        IFlashcard flashcard = new Flashcard(pregunta, respuesta, testDeck);

        assertNotNull(flashcard.getId());
        assertEquals(pregunta, flashcard.getPregunta());
        assertEquals(respuesta, flashcard.getRespuesta());
        assertNotNull(flashcard.getCreatedAt());
        assertNotNull(flashcard.getUpdatedAt());
    }

    @Test
    void testSetPregunta() {
        IFlashcard flashcard = new Flashcard("Pregunta", "Respuesta",  testDeck);

        flashcard.setPregunta("Nueva pregunta");

        assertEquals("Nueva pregunta", flashcard.getPregunta());
    }

    @Test
    void testSetRespuesta() {
        IFlashcard flashcard = new Flashcard("Pregunta", "Respuesta", testDeck);

        flashcard.setRespuesta("Nueva respuesta");

        assertEquals("Nueva respuesta", flashcard.getRespuesta());
    }

    @Test
    void testSetUpdatedAt() {
        IFlashcard flashcard = new Flashcard("Pregunta", "Respuesta", testDeck);
        LocalDateTime now = LocalDateTime.now();

        flashcard.setUpdatedAt(now);

        assertEquals(now, flashcard.getUpdatedAt());
    }

    @Test
    void testIdUnico() {
        IFlashcard f1 = new Flashcard("P1", "R1", testDeck);
        IFlashcard f2 = new Flashcard("P2", "R2", testDeck);

        assertNotEquals(f1.getId(), f2.getId());
    }

    @Test
    void setNextReviewDate_deberiaActualizarCorrectamente() {
        IFlashcard flashcard = new Flashcard("Pregunta", "Respuesta", testDeck);
        LocalDateTime nextReviewDate = LocalDateTime.now().plusDays(1);
        flashcard.setNextReviewDate(nextReviewDate);
        assertEquals(nextReviewDate, flashcard.getNextReviewDate());
    }
}

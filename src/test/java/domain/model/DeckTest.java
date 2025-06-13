package domain.model;

import edu.utn.domain.model.deck.Deck;
import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void testDeckSeteoDeCampos() {
        String nombre = "Deck de prueba";
        String descripcion = "Deck de prueba para testear la clase Deck";

        IDeck deck = new Deck(nombre, descripcion);

        assertNotNull(deck.getId());
        assertEquals(nombre, deck.getNombre());
        assertEquals(descripcion, deck.getDescripcion());
        assertNotNull(deck.getCreatedAt());
        assertNull(deck.getUpdatedAt());
        assertNotNull(deck.getFlashcards());
        assertTrue(deck.getFlashcards().isEmpty());
    }

    @Test
    void testSetNombre() {
        IDeck deck = new Deck("Deck 1", "Descripción 1");
        deck.setNombre("Deck 2");

        assertEquals("Deck 2", deck.getNombre());
    }

    @Test
    void testSetDescripcion() {
        IDeck deck = new Deck("Deck 1", "Descripción 1");
        deck.setDescripcion("Descripción 2");

        assertEquals("Descripción 2", deck.getDescripcion());
    }

    @Test
    void testSetUpdatedAt() {
        IDeck deck = new Deck("Deck 1", "Descripción 1");
        LocalDateTime now = LocalDateTime.now();
        deck.setUpdatedAt(now);

        assertEquals(now, deck.getUpdatedAt());
    }

    @Test
    void testAddFlashcardToDeck() {
        IDeck deck = new Deck("Deck 1", "Descripción 1");

        IFlashcard flashcard = new Flashcard("Pregunta", "Respuesta", deck);
        deck.getFlashcards().add(flashcard);

        assertEquals(1, deck.getFlashcards().size());
        assertEquals(flashcard, deck.getFlashcards().get(0));
    }

    @Test
    void testIdUnico() {
        IDeck d1 = new Deck("Deck 1", "Descripción 1");
        IDeck d2 = new Deck("Deck 2", "Descripción 2");

        assertNotEquals(d1.getId(), d2.getId());
    }
}
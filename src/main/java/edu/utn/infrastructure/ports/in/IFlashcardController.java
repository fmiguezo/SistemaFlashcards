package edu.utn.infrastructure.ports.in;
import edu.utn.application.dto.FlashcardDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.UUID;

public interface IFlashcardController {

    // Crear una flashcard en un mazo
    ResponseEntity<?> createFlashcard(@PathVariable UUID deckId, @RequestBody FlashcardDTO flashcardDto);

    // Modificar una flashcard
    ResponseEntity<?> modifyFlashcard(@PathVariable UUID cardId, @RequestBody FlashcardDTO flashcardDto);

    // Eliminar una flashcard
    ResponseEntity<?> deleteFlashcard(@PathVariable UUID cardId);

    // Listar flashcards de un mazo
    ResponseEntity<?> listFlashcards(@PathVariable UUID deckId);
}

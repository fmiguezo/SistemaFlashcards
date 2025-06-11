package edu.utn.infrastructure.adapters.in.rest.controller;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.usecase.flashcard.CreateFlashcardUseCase;
import edu.utn.application.usecase.deck.DeleteFlashcardUseCase;
import edu.utn.application.usecase.flashcard.ModifyFlashcardUseCase;
import edu.utn.infrastructure.ports.in.IFlashcardController;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController implements IFlashcardController {

    private final CreateFlashcardUseCase createFlashcardUseCase;
    private final ModifyFlashcardUseCase modifyFlashcardUseCase;
    private final DeleteFlashcardUseCase deleteFlashcardUseCase;

    public FlashcardController(CreateFlashcardUseCase createFlashcardUseCase, ModifyFlashcardUseCase modifyFlashcardUseCase, DeleteFlashcardUseCase deleteFlashcardUseCase) {
        this.createFlashcardUseCase = createFlashcardUseCase;
        this.modifyFlashcardUseCase = modifyFlashcardUseCase;
        this.deleteFlashcardUseCase = deleteFlashcardUseCase;
    }


    @PostMapping("/deck/{deckId}")
    @Override
    public ResponseEntity<?> createFlashcard(@PathVariable UUID deckId, @RequestBody FlashcardDTO flashcardDto) {
        FlashcardDTO result = createFlashcardUseCase.execute(flashcardDto);
        return ResponseEntity.ok("Se creo la flash card: " + result.getPregunta() + " correctamente");
    }

    @PutMapping("/{flashcardId}")
    @Override
    public ResponseEntity<?> modifyFlashcard(@PathVariable UUID flashcardId, @RequestBody FlashcardDTO flashcardDto) {
        FlashcardDTO result = modifyFlashcardUseCase.execute(flashcardDto);
        return ResponseEntity.ok("Se modifico la flash card: " + result.getPregunta() + " correctamente");
    }

    @DeleteMapping("/{flashcardId}")
    @Override
    public ResponseEntity<?> deleteFlashcard(@PathVariable UUID flashcardId) {
        FlashcardDTO result = deleteFlashcardUseCase.execute(flashcardId);
        return ResponseEntity.ok("Se elimino la flash card: " + result.getPregunta() + " correctamente");
    }

    
}

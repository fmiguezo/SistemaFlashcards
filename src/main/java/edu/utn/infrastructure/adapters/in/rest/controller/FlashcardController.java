package edu.utn.infrastructure.adapters.in.rest.controller;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.usecase.CreateFlashcardUseCase;
import edu.utn.application.usecase.DeleteFlashcardUseCase;
import edu.utn.application.usecase.ListFlashcardsUseCase;
import edu.utn.application.usecase.ModifyFlashcardUseCase;
import edu.utn.infrastructure.ports.in.IFlashcardController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController implements IFlashcardController {

    private final CreateFlashcardUseCase createFlashcardUseCase;
    private final ModifyFlashcardUseCase modifyFlashcardUseCase;
    private final DeleteFlashcardUseCase deleteFlashcardUseCase;
    private final ListFlashcardsUseCase listFlashcardsUseCase;

    public FlashcardController(CreateFlashcardUseCase createFlashcardUseCase, ModifyFlashcardUseCase modifyFlashcardUseCase, DeleteFlashcardUseCase deleteFlashcardUseCase, ListFlashcardsUseCase listFlashcardsUseCase) {
        this.createFlashcardUseCase = createFlashcardUseCase;
        this.modifyFlashcardUseCase = modifyFlashcardUseCase;
        this.deleteFlashcardUseCase = deleteFlashcardUseCase;
        this.listFlashcardsUseCase = listFlashcardsUseCase;
    }


    @PostMapping("/deck/{deckId}")
    @Override
    public ResponseEntity<?> createFlashcard(@PathVariable UUID deckId, @RequestBody FlashcardDTO flashcardDto) {
        FlashcardDTO result = createFlashcardUseCase.createFlashcard(deckId, flashcardDto);
        return ResponseEntity.ok("Se creo la flash card: " + result.getPregunta() + " correctamente");
    }

    @PutMapping("/{flashcardId}")
    @Override
    public ResponseEntity<?> modifyFlashcard(@PathVariable UUID flashcardId, @RequestBody FlashcardDTO flashcardDto) {
        FlashcardDTO result = modifyFlashcardUseCase.modifyFlashcard(flashcardId, flashcardDto);
        return ResponseEntity.ok("Se modifico la flash card: " + result.getPregunta() + " correctamente");
    }

    @DeleteMapping("/{flashcardId}")
    @Override
    public ResponseEntity<?> deleteFlashcard(@PathVariable UUID flashcardId) {
        FlashcardDTO result = deleteFlashcardUseCase.deleteFlashcard(flashcardId);
        return ResponseEntity.ok("Se elimino la flash card: " + result.getPregunta() + " correctamente");
    }

    @GetMapping("/deck/{deckId}")
    @Override
    public ResponseEntity<?> listFlashcards(@PathVariable UUID deckId) {
        ArrayList<FlashcardDTO> result = listFlashcardsUseCase.listFlashcards(deckId);
        return ResponseEntity.ok(result);
    }
}

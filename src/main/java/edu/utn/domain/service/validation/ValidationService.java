package edu.utn.domain.service.validation;

import edu.utn.application.error.DeckError;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.domain.service.deck.IDeckService;
import edu.utn.domain.service.flashcard.IFlashcardService;

public class ValidationService {
    private static final int FLASHCARD_QUESTION_MIN_LENGTH = 5;
    private static final int FLASHCARD_QUESTION_MAX_LENGTH = 100;
    private static final int FLASHCARD_ANSWER_MIN_LENGTH = 5;
    private static final int FLASHCARD_ANSWER_MAX_LENGTH = 250;

    private static final int DECK_NAME_MAX_LENGTH = 100;
    private static final int DECK_DESCRIPTION_MAX_LENGTH = 250;

    public void validateFlashcardInput(FlashcardDTO flashcardDTO) {
        if (flashcardDTO == null) {
            throw FlashcardError.nullFlashcard();
        }

        String pregunta = flashcardDTO.getPregunta();
        if (pregunta == null || pregunta.trim().isEmpty()) {
            throw FlashcardError.emptyQuestion();
        }
        
        if (pregunta.length() > FLASHCARD_QUESTION_MAX_LENGTH) {
            throw FlashcardError.questionTooLong();
        }
        
        String respuesta = flashcardDTO.getRespuesta();
        if (respuesta == null || respuesta.trim().isEmpty() || respuesta.equals("")) {
            throw FlashcardError.emptyAnswer();
        }
        
        if (respuesta.length() > FLASHCARD_ANSWER_MAX_LENGTH) {
            throw FlashcardError.answerTooLong();
        }
    }

    public FlashcardDTO validateFlashcardModification(FlashcardDTO flashcardDTO, IFlashcardService flashcardService, String pregunta, String respuesta) {
        if (pregunta == null && respuesta == null) {
            throw FlashcardError.noFieldsToModify();
        }

        if (pregunta != null && pregunta.trim().isEmpty()) {
            throw FlashcardError.emptyQuestion();
        }

        if (respuesta != null && respuesta.trim().isEmpty()) {
            throw FlashcardError.emptyAnswer();
        }

        if (flashcardDTO == null) {
            throw FlashcardError.nullFlashcard();
        }

        if (flashcardDTO.getId() == null) {
            throw FlashcardError.nullFlashcardId();
        }

        FlashcardDTO existingFlashcard = flashcardService.getFlashcardById(flashcardDTO.getId());
        if (existingFlashcard == null) {
            throw FlashcardError.flashcardNotFound();
        }

        if (flashcardDTO.getPregunta() != null) {
            if (pregunta == null) {
                throw FlashcardError.emptyQuestion();
            }
            if (pregunta.length() < FLASHCARD_QUESTION_MIN_LENGTH) {
                throw FlashcardError.questionTooShort();
            }
            if (pregunta.length() > FLASHCARD_QUESTION_MAX_LENGTH) {
                throw FlashcardError.questionTooLong();
            }
            if (flashcardDTO.getPregunta().equals(pregunta) && respuesta == null) {
                throw FlashcardError.sameQuestion();
            }
        }

        if (flashcardDTO.getRespuesta() != null) {
            if (respuesta == null) {
                throw FlashcardError.emptyAnswer();
            }
            if (respuesta.length() < FLASHCARD_ANSWER_MIN_LENGTH) {
                throw FlashcardError.answerTooShort();
            }
            if (respuesta.length() > FLASHCARD_ANSWER_MAX_LENGTH) {
                throw FlashcardError.answerTooLong();
            }
            if (flashcardDTO.getRespuesta().equals(respuesta) && pregunta == null) {
                throw FlashcardError.sameAnswer();
            }
        }

        return existingFlashcard;
    }

    public void validateDeckInput(DeckDTO deckDTO) {
        if (deckDTO == null) {
            throw DeckError.nullDeck();
        }

        String nombre = deckDTO.getNombre();
        if (nombre == null || nombre.trim().isEmpty()) {
            throw DeckError.emptyName();
        }
        
        if (nombre.length() > DECK_NAME_MAX_LENGTH) {
            throw DeckError.nameTooLong();
        }
        
        String descripcion = deckDTO.getDescripcion();
        if (descripcion != null && descripcion.length() > DECK_DESCRIPTION_MAX_LENGTH) {
            throw DeckError.descriptionTooLong();
        }
    }

    public DeckDTO validateDeckModification(DeckDTO deckDTO, IDeckService deckService) {
        if (deckDTO == null) {
            throw DeckError.nullDeck();
        }

        if (deckDTO.getId() == null) {
            throw DeckError.nullDeckId();
        }

        if (deckDTO.getNombre() == null && deckDTO.getDescripcion() == null) {
            throw DeckError.noFieldsToModify();
        }

        DeckDTO existingDeck = deckService.getDeckById(deckDTO.getId());
        if (existingDeck == null) {
            throw DeckError.deckNotFound();
        }

        if (deckDTO.getNombre() != null) {
            if (deckDTO.getNombre().trim().isEmpty()) {
                throw DeckError.emptyName();
            }
            if (deckDTO.getNombre().length() > DECK_NAME_MAX_LENGTH) {
                throw DeckError.nameTooLong();
            }
            if (deckDTO.getNombre().equals(existingDeck.getNombre())) {
                throw DeckError.sameName();
            }
        }

        if (deckDTO.getDescripcion() != null) {
            if (deckDTO.getDescripcion().length() > DECK_DESCRIPTION_MAX_LENGTH) {
                throw DeckError.descriptionTooLong();
            }
            if (deckDTO.getDescripcion().equals(existingDeck.getDescripcion())) {
                throw DeckError.sameDescription();
            }
        }

        return existingDeck;
    }
} 
package edu.utn.domain.service.validation;

import edu.utn.application.error.DeckError;
import edu.utn.application.error.FlashcardError;
import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.domain.service.deck.IDeckService;
import edu.utn.domain.service.flashcard.IFlashcardService;

public class ValidationService {
    private static final int FLASHCARD_QUESTION_MIN_LENGTH = 10;
    private static final int FLASHCARD_QUESTION_MAX_LENGTH = 100;
    private static final int FLASHCARD_ANSWER_MIN_LENGTH = 10;
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
        if (respuesta == null || respuesta.trim().isEmpty()) {
            throw FlashcardError.emptyAnswer();
        }
        
        if (respuesta.length() > FLASHCARD_ANSWER_MAX_LENGTH) {
            throw FlashcardError.answerTooLong();
        }
    }

    public IFlashcard validateFlashcardModification(FlashcardDTO flashcardDTO, IFlashcardService flashcardService) {
        if (flashcardDTO == null) {
            throw FlashcardError.nullFlashcard();
        }

        if (flashcardDTO.getId() == null) {
            throw FlashcardError.nullFlashcardId();
        }

        if (flashcardDTO.getPregunta() == null && flashcardDTO.getRespuesta() == null) {
            throw FlashcardError.noFieldsToModify();
        }

        IFlashcard existingFlashcard = flashcardService.getFlashcardById(flashcardDTO.getId());
        if (existingFlashcard == null) {
            throw FlashcardError.flashcardNotFound();
        }

        if (flashcardDTO.getPregunta() != null) {
            if (flashcardDTO.getPregunta().trim().isEmpty()) {
                throw FlashcardError.emptyQuestion();
            }
            if (flashcardDTO.getPregunta().length() < FLASHCARD_QUESTION_MIN_LENGTH) {
                throw FlashcardError.questionTooShort();
            }
            if (flashcardDTO.getPregunta().length() > FLASHCARD_QUESTION_MAX_LENGTH) {
                throw FlashcardError.questionTooLong();
            }
            if (flashcardDTO.getPregunta().equals(existingFlashcard.getPregunta())) {
                throw FlashcardError.sameQuestion();
            }
        }

        if (flashcardDTO.getRespuesta() != null) {
            if (flashcardDTO.getRespuesta().trim().isEmpty()) {
                throw FlashcardError.emptyAnswer();
            }
            if (flashcardDTO.getRespuesta().length() < FLASHCARD_ANSWER_MIN_LENGTH) {
                throw FlashcardError.answerTooShort();
            }
            if (flashcardDTO.getRespuesta().length() > FLASHCARD_ANSWER_MAX_LENGTH) {
                throw FlashcardError.answerTooLong();
            }
            if (flashcardDTO.getRespuesta().equals(existingFlashcard.getRespuesta())) {
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

    public IDeck validateDeckModification(DeckDTO deckDTO, IDeckService deckService) {
        if (deckDTO == null) {
            throw DeckError.nullDeck();
        }

        if (deckDTO.getId() == null) {
            throw DeckError.nullDeckId();
        }

        if (deckDTO.getNombre() == null && deckDTO.getDescripcion() == null) {
            throw DeckError.noFieldsToModify();
        }

        IDeck existingDeck = deckService.getDeckById(deckDTO.getId());
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
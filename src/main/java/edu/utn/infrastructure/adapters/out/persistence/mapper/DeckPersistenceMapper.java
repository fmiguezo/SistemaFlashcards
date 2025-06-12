package edu.utn.infrastructure.adapters.out.persistence.mapper;

import edu.utn.domain.model.deck.Deck;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.adapters.out.persistence.entities.DeckEntity;
import edu.utn.infrastructure.adapters.out.persistence.entities.FlashcardEntity;


import java.util.List;
import java.util.stream.Collectors;

public class DeckPersistenceMapper {

    private final FlashcardPersistenceMapper flashcardPersistenceMapper;

    public DeckPersistenceMapper() {
        this.flashcardPersistenceMapper = new FlashcardPersistenceMapper();
    }

    public IDeck toDomain(DeckEntity entity) {
        if (entity == null) return null;

        IDeck deck = new Deck(entity.getNombre(), entity.getDescripcion());

        deck.setUpdatedAt(entity.getUpdatedAt());

        List<IFlashcard> flashcards = entity.getFlashcards()
                .stream()
                .map(flashcardPersistenceMapper::toDomain)
                .collect(Collectors.toList());

        flashcards.forEach(deck::addFlashcard);

        return deck;
    }

    public static DeckEntity toPersistence(IDeck deck) {
        if (deck == null) return null;

        DeckEntity entity = new DeckEntity(deck.getNombre(), deck.getDescripcion());
        entity.setId(deck.getId());
        entity.setUpdatedAt(deck.getUpdatedAt());

        List<FlashcardEntity> flashcardsEntities = deck.getFlashcards()
                .stream()
                .map(f -> FlashcardPersistenceMapper.toPersistence((Flashcard) f)) // casteo porque en dominio es interface
                .collect(Collectors.toList());

        entity.getFlashcards().addAll(flashcardsEntities);

        return entity;
    }
}
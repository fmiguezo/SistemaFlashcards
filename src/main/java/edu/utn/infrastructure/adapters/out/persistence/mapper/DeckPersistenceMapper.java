package edu.utn.infrastructure.adapters.out.persistence.mapper;

import edu.utn.domain.model.deck.Deck;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.Flashcard;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.adapters.out.persistence.entities.DeckEntity;
import edu.utn.infrastructure.adapters.out.persistence.entities.FlashcardEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeckPersistenceMapper {

    private final FlashcardPersistenceMapper flashcardPersistenceMapper;

    public DeckPersistenceMapper(FlashcardPersistenceMapper flashcardPersistenceMapper) {
        this.flashcardPersistenceMapper = flashcardPersistenceMapper;
    }

    @Transactional
    public IDeck toDomain(DeckEntity entity) {
        if (entity == null) return null;

        IDeck deck = new Deck(entity.getNombre(), entity.getDescripcion());
        deck.setId(entity.getId());
        deck.setCreatedAt(entity.getCreatedAt());
        deck.setUpdatedAt(entity.getUpdatedAt());

        List<IFlashcard> flashcards = entity.getFlashcards()
                .stream()
                .map(flashcardPersistenceMapper::toDomain)
                .collect(Collectors.toList());

        flashcards.forEach(deck::addFlashcard);

        return deck;
    }

    @Transactional
    public DeckEntity toPersistence(IDeck deck) {
        if (deck == null) return null;

        DeckEntity entity = new DeckEntity(deck.getNombre(), deck.getDescripcion());
        entity.setId(deck.getId());
        entity.setUpdatedAt(deck.getUpdatedAt());

        List<FlashcardEntity> flashcardsEntities = deck.getFlashcards()
                .stream()
                .map(f -> FlashcardPersistenceMapper.toPersistence((Flashcard) f, entity))
                .collect(Collectors.toList());

        entity.getFlashcards().clear();
        entity.getFlashcards().addAll(flashcardsEntities);

        return entity;
    }
}
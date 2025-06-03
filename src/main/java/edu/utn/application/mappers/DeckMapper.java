package edu.utn.application.mappers;
import edu.utn.domain.model.Deck;
import edu.utn.domain.model.Flashcard;
import edu.utn.infrastructure.adapters.out.entity.DeckEntity;
import edu.utn.infrastructure.adapters.out.entity.FlashcardEntity;
import edu.utn.infrastructure.adapters.out.jsonmodel.DeckJson;
import edu.utn.infrastructure.adapters.out.jsonmodel.FlashcardJson;
import edu.utn.infrastructure.adapters.in.rest.dto.CreateDeckRequest;
import edu.utn.infrastructure.adapters.in.rest.dto.DeckResponse;
import edu.utn.infrastructure.adapters.in.rest.dto.FlashcardResponse;

import java.util.List;
import java.util.stream.Collectors;

public class DeckMapper {

    // ENTITY <-> DOMAIN
    public static Deck toDomain(DeckEntity entity) {
        List<Flashcard> flashcards = entity.getFlashcards().stream()
                .map(FlashcardMapper::toDomain)
                .collect(Collectors.toList());

        Deck deck = new Deck(entity.getId(), entity.getNombre(), entity.getDescripcion(), flashcards);
        deck.setCreatedAt(entity.getCreatedAt());
        deck.setUpdatedAt(entity.getUpdatedAt());

        return deck;
    }

    public static DeckEntity toEntity(Deck deck) {
        DeckEntity deckEntity = new DeckEntity(deck.getId(), deck.getNombre(), deck.getDescripcion(), null);
        deckEntity.setCreatedAt(deck.getCreatedAt());
        deckEntity.setUpdatedAt(deck.getUpdatedAt());

        List<FlashcardEntity> flashcardEntities = deck.getFlashcards().stream()
                .map(flashcard -> FlashcardMapper.toEntity(flashcard, deckEntity))
                .collect(Collectors.toList());

        deckEntity.setFlashcards(flashcardEntities);

        return deckEntity;
    }

    // JSON <-> DOMAIN
    public static Deck toDomain(DeckJson json) {
        List<Flashcard> flashcards = json.getFlashcards().stream()
                .map(FlashcardMapper::toDomain)
                .collect(Collectors.toList());

        Deck deck = new Deck(json.getId(), json.getNombre(), json.getDescripcion(), flashcards);
        deck.setCreatedAt(json.getCreatedAt());
        deck.setUpdatedAt(json.getUpdatedAt());

        return deck;
    }

    public static DeckJson toJson(Deck deck) {
        List<FlashcardJson> flashcardJsons = deck.getFlashcards().stream()
                .map(FlashcardMapper::toJson)
                .collect(Collectors.toList());

        DeckJson json = new DeckJson(deck.getId(), deck.getNombre(), deck.getDescripcion(), flashcardJsons);
        json.setCreatedAt(deck.getCreatedAt());
        json.setUpdatedAt(deck.getUpdatedAt());

        return json;
    }

    // CONTROLLER DTOs
    public static Deck toDomain(CreateDeckRequest request) {
        List<Flashcard> flashcards = request.getFlashcards().stream()
                .map(FlashcardMapper::toDomain)
                .collect(Collectors.toList());

        return new Deck(null, request.getNombre(), request.getDescripcion(), flashcards);
    }

    public static DeckResponse toResponse(Deck deck) {
        List<FlashcardResponse> flashcardResponses = deck.getFlashcards().stream()
                .map(FlashcardMapper::toResponse)
                .collect(Collectors.toList());

        DeckResponse response = new DeckResponse(
                deck.getId(),
                deck.getNombre(),
                deck.getDescripcion(),
                flashcardResponses
        );
        response.setCreatedAt(deck.getCreatedAt());
        response.setUpdatedAt(deck.getUpdatedAt());

        return response;
    }
}

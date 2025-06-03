package edu.utn.application.mappers;
import edu.utn.domain.model.Flashcard;
import edu.utn.infrastructure.adapters.out.entity.DeckEntity;
import edu.utn.infrastructure.adapters.out.entity.FlashcardEntity;
import edu.utn.infrastructure.adapters.out.jsonmodel.FlashcardJson;
import edu.utn.infrastructure.adapters.in.rest.dto.CreateFlashcardRequest;
import edu.utn.infrastructure.adapters.in.rest.dto.FlashcardResponse;

public class FlashcardMapper {

    // ENTITY <-> DOMAIN
    public static Flashcard toDomain(FlashcardEntity entity) {
        return new Flashcard(entity.getId(), entity.getFront(), entity.getBack());
    }

    public static FlashcardEntity toEntity(Flashcard flashcard, DeckEntity deckEntity) {
        return new FlashcardEntity(flashcard.getId(), flashcard.getFront(), flashcard.getBack(), deckEntity);
    }

    // JSON <-> DOMAIN
    public static Flashcard toDomain(FlashcardJson json) {
        return new Flashcard(json.getId(), json.getFront(), json.getBack());
    }

    public static FlashcardJson toJson(Flashcard flashcard) {
        return new FlashcardJson(flashcard.getId(), flashcard.getFront(), flashcard.getBack());
    }

    // CONTROLLER DTOs
    public static Flashcard toDomain(CreateFlashcardRequest request) {
        return new Flashcard(null, request.getPregunta(), request.getRespuesta());
    }

    public static FlashcardResponse toResponse(Flashcard flashcard) {
        return new FlashcardResponse(
                flashcard.getId(),
                flashcard.getFront(),
                flashcard.getBack());
    }
}

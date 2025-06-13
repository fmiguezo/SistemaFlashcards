package edu.utn.infrastructure.ports.out;

import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.adapters.out.persistence.entities.FlashcardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaFlashcardRepository extends JpaRepository<FlashcardEntity, UUID> {
    List<FlashcardEntity> findByDeckId(UUID deckId);
}
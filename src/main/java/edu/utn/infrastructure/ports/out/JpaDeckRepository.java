package edu.utn.infrastructure.ports.out;

import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.adapters.out.persistence.entities.DeckEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaDeckRepository extends JpaRepository<DeckEntity, UUID> {
    List<DeckEntity> findAll();
    Optional<DeckEntity> findDeckById(UUID id);
    IDeck saveDeck(DeckEntity deck);
    void deleteDeckById(UUID id);
    void deleteCardById(UUID id);
    void updateDeck(DeckEntity deck);

}


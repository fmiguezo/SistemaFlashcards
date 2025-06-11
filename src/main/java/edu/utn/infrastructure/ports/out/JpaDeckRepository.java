package edu.utn.infrastructure.ports.out;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface JpaDeckRepository extends JpaRepository<IDeck, UUID> {
    List<IDeck> findAll();
    Optional<IDeck> findDeckById(UUID id);
    Optional<IFlashcard> findCardById(UUID nombre);
    IDeck saveDeck(IDeck deck);
    IFlashcard saveFlashcard(IFlashcard flashcardRepository);
    void deleteDeckById(UUID id);
    void deleteCardById(UUID id);
    void updateDeck(IDeck deck);
    void updateFlashcard(IFlashcard flashcard);
}


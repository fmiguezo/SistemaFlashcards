package edu.utn.infrastructure.ports.out;
import edu.utn.domain.model.Deck;
import edu.utn.domain.model.IDeck;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface JpaDeckRepository extends JpaRepository<IDeck, UUID> {
    List<IDeck> findAll();
    Optional<IDeck> findById(UUID id);
    IDeck save(IDeck deck);
    void deleteById(UUID id);
    void updateDeck(IDeck deck);
}


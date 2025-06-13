package edu.utn.infrastructure.adapters.out.persistence.firebase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.*;
import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.mappers.DeckMapper;
import edu.utn.application.mappers.FlashcardMapper;
import edu.utn.domain.model.deck.IDeck;
import edu.utn.domain.model.flashcard.IFlashcard;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Profile("firebase")
public class FirebaseDeckService implements IDeckRepository {

    private final DatabaseReference decksRef;
    private final ObjectMapper mapper = new ObjectMapper();

    public FirebaseDeckService() {
        this.decksRef = FirebaseDatabase.getInstance()
                .getReference("decks");
    }

    @Override
    public List<IDeck> getAllDecks() {
        List<IDeck> out = new ArrayList<>();

        final Object lock = new Object();

        decksRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        DeckDTO deckDTO = mapper.convertValue(child.getValue(), DeckDTO.class);
                        IDeck deck = DeckMapper.toDomain(deckDTO);
                        out.add(deck);
                    }
                }
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error leyendo decks: " + error.getMessage());
                synchronized (lock) {
                    lock.notify();
                }
            }
        });

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return out;
    }

    @Override
    public Optional<IDeck> getDeckById(UUID id) {
        final Object lock = new Object();
        final List<IDeck> result = new ArrayList<>(1);

        decksRef.child(id.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    DeckDTO deckDTO = mapper.convertValue(snapshot.getValue(), DeckDTO.class);
                    IDeck deck = DeckMapper.toDomain(deckDTO);
                    result.add(deck);
                }
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Error leyendo deck: " + error.getMessage());
                synchronized (lock) {
                    lock.notify();
                }
            }
        });

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public void createDeck(IDeck deck) {
        DeckDTO deckDTO = DeckMapper.toDTO(deck);
        Map<String, Object> data = mapper.convertValue(deckDTO, Map.class);
        decksRef.child(deck.getId().toString()).setValueAsync(data);
    }

    @Override
    public void updateDeck(IDeck deck) {
        DeckDTO deckDTO = DeckMapper.toDTO(deck);
        Map<String, Object> data = mapper.convertValue(deckDTO, Map.class);
        decksRef.child(deck.getId().toString()).updateChildrenAsync(data);
    }

    @Override
    public void deleteDeckById(UUID id) {
        decksRef.child(id.toString()).removeValueAsync();
    }

    @Override
    public List<IFlashcard> getFlashcardsByDeckId(UUID deckId) {
        List<IFlashcard> out = new ArrayList<>();

        final Object lock = new Object();

        decksRef.child(deckId.toString())
                .child("flashcards")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot child : snapshot.getChildren()) {
                                FlashcardDTO flashcardDTO = mapper.convertValue(child.getValue(), FlashcardDTO.class);
                                IFlashcard flashcard = FlashcardMapper.toDomain(flashcardDTO);
                                out.add(flashcard);
                            }
                        }
                        synchronized (lock) {
                            lock.notify();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        System.err.println("Error leyendo flashcards: " + error.getMessage());
                        synchronized (lock) {
                            lock.notify();
                        }
                    }
                });

        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return out;
    }
}


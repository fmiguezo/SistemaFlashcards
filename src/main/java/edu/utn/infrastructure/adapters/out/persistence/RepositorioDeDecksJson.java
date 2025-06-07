package edu.utn.infrastructure.adapters.out.persistence;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.utn.domain.model.Deck;
import edu.utn.domain.model.IDeck;
import edu.utn.domain.model.IFlashcard;
import edu.utn.infrastructure.adapters.out.exception.ArchivoNoEncontradoException;
import edu.utn.infrastructure.adapters.out.exception.DeckVacioException;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Profile("json") //Esto se activa cuando el perfil está en JSON
public class RepositorioDeDecksJson implements IDeckRepository {
    private final String jsonFilePath;
    private final ObjectMapper objectMapper;

    public RepositorioDeDecksJson(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<IDeck> getAllDecks() {
        try {
            File file = new File(jsonFilePath);
            if (!file.exists()) {
                throw new ArchivoNoEncontradoException("El archivo JSON no existe en la ruta especificada: " + jsonFilePath);
            }

            // Leer el archivo JSON y convertirlo a un objeto que contiene la lista de Decks
            JsonNode rootNode = objectMapper.readTree(file);
            JsonNode decksNode = rootNode.get("decks");

            if (decksNode == null || !decksNode.isArray()) {
                throw new DeckVacioException("El archivo JSON no contiene una lista de decks válida o está vacío.");
            }

            // Convertir cada nodo del array a un Deck
            List<IDeck> decks = new ArrayList<>();
            for (JsonNode deckNode : decksNode) {
                Deck deck = objectMapper.treeToValue(deckNode, Deck.class);
                decks.add(deck);
            }

            return decks;

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public IDeck getDeckById(UUID id) {
        throw new UnsupportedOperationException("Unimplemented method 'getDeckById'");
    }

    @Override
    public void createDeck(IDeck deck) {
        throw new UnsupportedOperationException("Unimplemented method 'addDeck'");
    }

    @Override
    public void updateDeck(IDeck deck) {
        throw new UnsupportedOperationException("Unimplemented method 'updateDeck'");
    }

    @Override
    public void deleteDeckById(UUID id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteDeck'");
    }

    @Override
    public List<IFlashcard> getFlashcardsByDeckId(UUID deckId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFlashcardsByDeckId'");
    }
}

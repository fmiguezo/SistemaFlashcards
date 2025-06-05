package edu.utn.infrastructure.adapters.out.persistence;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.utn.domain.model.Flashcard;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Repository
@Profile("json") //Esto se activa cuando el perfil está en JSON
public class RepositorioDeCardsJson implements IFlashcardRepository {
    private final String jsonFilePath;
    private final ObjectMapper objectMapper;

    public RepositorioDeCardsJson(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<Flashcard> getCards(String deckId) {
        try {
            File file = new File(jsonFilePath);
            if (!file.exists()) {
                return new ArrayList<>();
            }

            // Leer el archivo JSON y convertirlo a un objeto que contiene la lista de Flashcards
            JsonNode rootNode = objectMapper.readTree(file);
            JsonNode cardsNode = rootNode.get("cards");

            if (cardsNode == null || !cardsNode.isArray()) {
                return new ArrayList<>();
                //Pasar a tirar una excepction despues
            }

            // Convertir cada nodo del array a una Flashcard y filtrar por deckId
            List<Flashcard> filteredCards = new ArrayList<>();
            for (JsonNode cardNode : cardsNode) {
                String cardDeckId = cardNode.get("deckId").asText();;
                if (cardDeckId == deckId) { // Esto es para solo transformar en flashcards a las que son del deckId que le pasamos, no todas alpedo.
                    Flashcard card = objectMapper.treeToValue(cardNode, Flashcard.class);
                    filteredCards.add(card);
                }
            }

            return filteredCards;

        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void updateCard(Flashcard card) {
        // Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCard'");
    }

    @Override
    public void deleteCard(Flashcard card) {
        //  Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCard'");
    }
}

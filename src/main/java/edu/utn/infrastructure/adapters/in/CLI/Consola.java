package edu.utn.infrastructure.adapters.in.CLI;


import edu.utn.application.dto.DeckDTO;
import edu.utn.application.dto.FlashcardDTO;
import edu.utn.application.usecase.deck.*;
import edu.utn.application.usecase.flashcard.*;
import edu.utn.domain.model.estrategia.EstrategiaRepeticionEstandar;
import edu.utn.domain.model.estrategia.IEstrategiaRepeticion;
import edu.utn.domain.service.deck.DeckService;
import edu.utn.domain.service.deck.IDeckService;
import edu.utn.domain.service.flashcard.FlashcardService;
import edu.utn.domain.service.flashcard.IFlashcardService;
import edu.utn.infrastructure.ports.in.IUserPracticeInputPort;
import edu.utn.infrastructure.ports.out.IDeckRepository;
import edu.utn.infrastructure.ports.out.IFlashcardRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class Consola implements CommandLineRunner {
    private final GetDeckUseCase getDeckUseCase;
    private final AddFlashcardToDeckUseCase addFlashcardToDeckUseCase;
    private IDeckService deckService;
    private IFlashcardService flashcardService;
    private IDeckRepository deckRepository;
    private IFlashcardRepository flashcardRepository;
    private IEstrategiaRepeticion estrategiaRepeticion;
    private IUserPracticeInputPort consola;
    private final CreateDeckUseCase createDeckUseCase;
    private final ListDecksUseCase listDecksUseCase;
    private final ModifyDeckUseCase modifyDeckUseCase;
    private final DeleteDeckUseCase deleteDeckUseCase;
    private final CreateFlashcardUseCase createFlashcardUseCase;
    private final ListFlashcardsUseCase listFlashcardsUseCase;
    private final ModifyFlashcardUseCase modifyFlashcardUseCase;
    private final DeleteFlashcardUseCase deleteFlashcardUseCase;
    private final PracticeDeckUseCase practiceDeckUseCase;

    public Consola(IDeckRepository deckRepository, IFlashcardRepository flashcardRepository, GetDeckUseCase getDeckUseCase, AddFlashcardToDeckUseCase addFlashcardToDeckUseCase) {
        this.deckRepository = deckRepository;
        this.flashcardRepository = flashcardRepository;
        this.consola = new ConsoleInput();
        this.estrategiaRepeticion = new EstrategiaRepeticionEstandar();
        this.flashcardService = new FlashcardService(this.flashcardRepository, this.consola);
        this.deckService = new DeckService(this.deckRepository, this.estrategiaRepeticion, this.flashcardService);
        this.createDeckUseCase = new CreateDeckUseCase(this.deckService);
        this.listDecksUseCase = new ListDecksUseCase(this.deckService);
        this.modifyDeckUseCase = new ModifyDeckUseCase(this.deckService);
        this.deleteDeckUseCase = new DeleteDeckUseCase(this.deckService);
        this.createFlashcardUseCase = new CreateFlashcardUseCase(this.flashcardService);
        this.listFlashcardsUseCase = new ListFlashcardsUseCase(this.deckService);
        this.modifyFlashcardUseCase = new ModifyFlashcardUseCase(this.flashcardService);
        this.deleteFlashcardUseCase = new DeleteFlashcardUseCase(this.flashcardService);
        this.practiceDeckUseCase = new PracticeDeckUseCase(this.deckService);
        this.getDeckUseCase = getDeckUseCase;
        this.addFlashcardToDeckUseCase = addFlashcardToDeckUseCase;
    }

    @Override
    public void run(String... args) {
        new Thread(this::iniciarMenuPrincipal).start();
    }

    @Async
    public void iniciarMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== BIENVENIDO AL SISTEMA DE FLASHCARDS ===");
            System.out.println("1. Ver mis decks");
            System.out.println("2. Crear un nuevo deck");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    verYSeleccionarDeck(scanner);
                    break;
                case "2":
                    crearNuevoDeck(scanner);
                    break;
                case "3":
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void crearNuevoDeck(Scanner scanner) {
        System.out.print("Ingrese el nombre del deck: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la descripción del deck: ");
        String descripcion = scanner.nextLine();
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            System.out.println("El nombre y la descripción no pueden estar vacíos.");
            return;
        }
        createDeckUseCase.execute(nombre, descripcion);
        System.out.println("Deck creado con éxito.");
    }

    private void verYSeleccionarDeck(Scanner scanner) {
        List<DeckDTO> decks = listDecksUseCase.execute();
        if (decks.isEmpty()) {
            System.out.println("No hay decks disponibles.");
            return;
        }

        for (int i = 0; i < decks.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, decks.get(i).getNombre());
        }

        System.out.print("Seleccione un deck: ");
        int seleccion = Integer.parseInt(scanner.nextLine());
        if (seleccion < 1 || seleccion > decks.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        DeckDTO seleccionado = decks.get(seleccion - 1);
        menuDeck(scanner, seleccionado);
    }

    private void menuDeck(Scanner scanner, DeckDTO deck) {
        while (true) {
            System.out.printf("%n--- Deck: %s ---\n", deck.getNombre());
            System.out.println("1. Editar");
            System.out.println("2. Eliminar");
            System.out.println("3. Ver flashcards");
            System.out.println("4. Practicar");
            System.out.println("5. Volver");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    editarDeck(scanner, deck);
                    break;
                case "2":
                    deleteDeckUseCase.execute(deck.getId());
                    System.out.println("Deck eliminado.");
                    return;
                case "3":
                    gestionarFlashcards(scanner, deck);
                    break;
                case "4":
                    practicarDeck(scanner, deck);
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void editarDeck(Scanner scanner, DeckDTO deck) {
        System.out.print("Nuevo nombre: ");
        String nuevoNombre = scanner.nextLine();
        System.out.print("Nueva descripción: ");
        String nuevaDescripcion = scanner.nextLine();
        deck.setNombre(nuevoNombre);
        deck.setDescripcion(nuevaDescripcion);
        modifyDeckUseCase.execute(deck);
        System.out.println("Deck actualizado.");
    }

    private void gestionarFlashcards(Scanner scanner, DeckDTO deck) {
        List<FlashcardDTO> flashcards = listFlashcardsUseCase.execute(deck.getId());

        if (flashcards.isEmpty()) {
            System.out.println("Este deck no tiene flashcards.");
        } else {
            for (int i = 0; i < flashcards.size(); i++) {
                FlashcardDTO f = flashcards.get(i);
                System.out.printf("%d. %s%n", i + 1, f.getPregunta());
            }
        }

        System.out.println("1. Agregar flashcard");
        System.out.println("2. Editar flashcard");
        System.out.println("3. Eliminar flashcard");
        System.out.println("4. Volver");
        System.out.print("Opción: ");
        String opcion = scanner.nextLine();

        switch (opcion) {
            case "1":
                agregarFlashcard(scanner, deck.getId());
                break;
            case "2":
                editarFlashcard(scanner, flashcards);
                break;
            case "3":
                eliminarFlashcard(scanner, flashcards);
                break;
            case "4":
                return;
            default:
                System.out.println("Opción inválida.");
        }
    }

    private void agregarFlashcard(Scanner scanner, UUID deckId) {
        System.out.print("Pregunta: ");
        String pregunta = scanner.nextLine();
        System.out.print("Respuesta: ");
        String respuesta = scanner.nextLine();
        FlashcardDTO flashcard = createFlashcardUseCase.execute(pregunta, respuesta);
        addFlashcardToDeckUseCase.execute(deckId, flashcard);
        System.out.println("Flashcard agregada.");
    }

    private void editarFlashcard(Scanner scanner, List<FlashcardDTO> flashcards) {
        System.out.print("Número de flashcard a editar: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        if (idx < 0 || idx >= flashcards.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        FlashcardDTO f = flashcards.get(idx);
        System.out.print("Nueva pregunta: ");
        String nuevaPregunta = scanner.nextLine();
        System.out.print("Nueva respuesta: ");
        String nuevaRespuesta = scanner.nextLine();
        modifyFlashcardUseCase.execute(f, nuevaPregunta, nuevaRespuesta);
        System.out.println("Flashcard actualizada.");
    }

    private void eliminarFlashcard(Scanner scanner, List<FlashcardDTO> flashcards) {
        System.out.print("Número de flashcard a eliminar: ");
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        if (idx < 0 || idx >= flashcards.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        deleteFlashcardUseCase.execute(flashcards.get(idx).getId());
        System.out.println("Flashcard eliminada.");
    }

    private void practicarDeck(Scanner scanner, DeckDTO deck) {
        practiceDeckUseCase.execute(deck, estrategiaRepeticion, consola);
        System.out.println("Fin de la práctica.");
    }
}
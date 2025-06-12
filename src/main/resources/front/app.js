// app.js

let decks = []; // Simulated data

const deckForm = document.getElementById("deckForm");
const deckIdInput = document.getElementById("deckId");
const deckNameInput = document.getElementById("deckName");
const deckTableBody = document.getElementById("deckTableBody");

const flashcardModal = new bootstrap.Modal(document.getElementById("flashcardModal"));
const flashcardFormModal = new bootstrap.Modal(document.getElementById("flashcardFormModal"));

const flashcardDeckName = document.getElementById("flashcardDeckName");
const flashcardTableBody = document.getElementById("flashcardTableBody");

let currentDeckIndex = null;

// Fetch all decks
async function fetchDecks() {
    try {
        const response = await fetch("/api/decks");
        const decksData = await response.json();
        decks = decksData;
        renderDecks();
    } catch (error) {
        console.error("Error fetching decks:", error);
    }
}

function renderDecks() {
    deckTableBody.innerHTML = "";
    decks.forEach((deck, index) => {
        deckTableBody.innerHTML += `
            <tr>
                <td>${deck.id}</td>
                <td>${deck.name}</td>
                <td>
                    <button class="btn btn-sm btn-info" onclick="viewFlashcards(${index})">View Flashcards</button>
                    <button class="btn btn-sm btn-warning" onclick="editDeck(${index})">Edit</button>
                    <button class="btn btn-sm btn-danger" onclick="deleteDeck(${index})">Delete</button>
                    <button class="btn btn-sm btn-primary" onclick="startPractice(${index})">Practice</button>
                </td>
            </tr>
        `;
    });
}

function editDeck(index) {
    const deck = decks[index];
    deckIdInput.value = deck.id;
    deckNameInput.value = deck.name;
    const modal = new bootstrap.Modal(document.getElementById("deckModal"));
    modal.show();
}

async function deleteDeck(index) {
    const deckId = decks[index].id;
    try {
        await fetch(`/api/decks/${deckId}`, { method: "DELETE" });
        decks.splice(index, 1);
        renderDecks();
    } catch (error) {
        console.error("Error deleting deck:", error);
    }
}

deckForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const deck = {
        id: deckIdInput.value,
        name: deckNameInput.value.trim(),
    };

    if (deck.id) {
        await updateDeck(deck);
    } else {
        await createDeck(deck);
    }

    deckForm.reset();
    bootstrap.Modal.getInstance(document.getElementById("deckModal")).hide();
    renderDecks();
});

async function createDeck(deck) {
    try {
        await fetch("/api/decks", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(deck),
        });
        fetchDecks();
    } catch (error) {
        console.error("Error creating deck:", error);
    }
}

async function updateDeck(deck) {
    try {
        await fetch(`/api/decks/${deck.id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(deck),
        });
        fetchDecks();
    } catch (error) {
        console.error("Error updating deck:", error);
    }
}

function viewFlashcards(index) {
    currentDeckIndex = index;
    const deck = decks[index];
    flashcardDeckName.textContent = deck.name;
    renderFlashcards(deck.flashcards);
    flashcardModal.show();
}

function renderFlashcards(flashcards) {
    flashcardTableBody.innerHTML = "";
    flashcards.forEach((fc, i) => {
        flashcardTableBody.innerHTML += `
            <tr>
                <td>${fc.question}</td>
                <td>${fc.answer}</td>
                <td>
                    <button class="btn btn-sm btn-warning" onclick="editFlashcard(${i})">Edit</button>
                    <button class="btn btn-sm btn-danger" onclick="deleteFlashcard(${i})">Delete</button>
                </td>
            </tr>
        `;
    });
}

function openFlashcardForm() {
    document.getElementById("flashcardForm").reset();
    document.getElementById("flashcardIndex").value = "";
    flashcardFormModal.show();
}

async function startPractice(index) {
    const deck = decks[index];
    try {
        await fetch(`/api/decks/${deck.id}/practice`, { method: "GET" });
    } catch (error) {
        console.error("Error starting practice:", error);
    }
}

async function deleteFlashcard(index) {
    const deck = decks[currentDeckIndex];
    const flashcardId = deck.flashcards[index].id;
    try {
        await fetch(`/api/flashcards/${flashcardId}`, { method: "DELETE" });
        deck.flashcards.splice(index, 1);
        renderFlashcards(deck.flashcards);
    } catch (error) {
        console.error("Error deleting flashcard:", error);
    }
}

renderDecks();
fetchDecks();

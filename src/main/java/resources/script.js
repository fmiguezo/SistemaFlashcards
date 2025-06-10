const deckId = "REEMPLAZAR_CON_EL_UUID_DEL_DECK"; // <-- Reemplazalo por tu deckId real

let flashcards = [];
let currentIndex = 0;

const questionEl = document.getElementById("question");
const answerEl = document.getElementById("answer");
const showAnswerBtn = document.getElementById("showAnswerBtn");
const responseButtons = document.getElementById("responseButtons");
const correctBtn = document.getElementById("correctBtn");
const wrongBtn = document.getElementById("wrongBtn");

async function fetchFlashcards() {
  try {
    const res = await fetch(`/api/decks/deck/${deckId}`);
    flashcards = await res.json();
    if (flashcards.length === 0) {
      questionEl.textContent = "No hay flashcards en este deck.";
      showAnswerBtn.style.display = "none";
    } else {
      showFlashcard();
    }
  } catch (error) {
    questionEl.textContent = "Error al cargar flashcards.";
    console.error(error);
  }
}

function showFlashcard() {
  const flashcard = flashcards[currentIndex];
  questionEl.textContent = flashcard.pregunta;
  answerEl.textContent = flashcard.respuesta;
  answerEl.classList.add("hidden");
  responseButtons.classList.add("hidden");
  showAnswerBtn.classList.remove("hidden");
}

showAnswerBtn.addEventListener("click", () => {
  answerEl.classList.remove("hidden");
  responseButtons.classList.remove("hidden");
  showAnswerBtn.classList.add("hidden");
});

function nextFlashcard() {
  currentIndex = (currentIndex + 1) % flashcards.length;
  showFlashcard();
}

correctBtn.addEventListener("click", () => {
  console.log("✅ Acertaste");
  nextFlashcard();
});

wrongBtn.addEventListener("click", () => {
  console.log("❌ No acertaste");
  nextFlashcard();
});

// Iniciar
fetchFlashcards();
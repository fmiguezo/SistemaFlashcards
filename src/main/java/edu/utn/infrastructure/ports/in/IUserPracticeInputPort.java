package edu.utn.infrastructure.ports.in;

import edu.utn.domain.model.IFlashcard;

public interface IUserPracticeInputPort {
    boolean isAnswerCorrect(IFlashcard flashcard);
}

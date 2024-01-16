package comp3350.studyplus.logic;

import comp3350.studyplus.objects.Question;

public interface IQuizHandler {
    Question getCurrentQuestion();

    int getTotalQuestion();

    int getCorrectAnswers();

    int getCurrentQuestionIndex();

    boolean getNextQuestion();

    int getFinalScore();

    boolean validateAnswer(String userAnswer);
}

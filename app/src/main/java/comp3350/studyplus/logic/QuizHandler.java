package comp3350.studyplus.logic;

import java.util.*;

import comp3350.studyplus.objects.Question;

public class QuizHandler implements IQuizHandler {
    private List<Question> questionListCopy;
    private Question currentQuestion;
    private int totalQuestion;
    private int currentQuestionIndex;
    private int correctAnswers;

    public QuizHandler(List<Question> questionList, int totalQuestionsSelected) {
        totalQuestion = totalQuestionsSelected;
        questionListCopy = new ArrayList<Question>();
        for (int i = 0; i < questionList.size(); i++) {
            questionListCopy.add(questionList.get(i));
        }
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public boolean validateAnswer(String answerInput) {
        if (currentQuestion != null && answerInput.trim().equalsIgnoreCase(currentQuestion.getAnswer())) {
            correctAnswers++;
            return true;
        }
        return false;
    }

    public boolean getNextQuestion() {
        if(currentQuestionIndex + 1 > totalQuestion) {
            currentQuestion = null;
            return false;
        }

        currentQuestionIndex++;
        int randomIndex = (int) (Math.random() * questionListCopy.size());
        currentQuestion = questionListCopy.get(randomIndex);
        questionListCopy.remove(randomIndex);
        return true;
    }

    public int getFinalScore() {
        return (int) Math.round( (double)correctAnswers / (double)totalQuestion * 100.0);
    }
}

package comp3350.studyplus.logic;

import comp3350.studyplus.application.Services;
import comp3350.studyplus.data.IQuestionPersistence;
import comp3350.studyplus.objects.Course;
import comp3350.studyplus.objects.Question;
import comp3350.studyplus.objects.QuestionTag;

import java.util.*;

public class QuestionHandler implements IQuestionHandler {
    private IQuestionPersistence questionPersistence;
    private List<Question> questions;

    public QuestionHandler() {
        questionPersistence = Services.getQuestionPersistence();
        questions = getQuestions();
    }

    public QuestionHandler(IQuestionPersistence questionPersistence) {
        this.questionPersistence = questionPersistence;
    }

    public List<Question> getQuestions() {
        questions = questionPersistence.getQuestionList();
        return questions;
    }

    public Question createQuestion(String question, String answer) {
        Question newQuestion = null;
        if (validateQuestionInputs(question, answer)) {
            newQuestion = new Question(question.trim(), answer.trim());
            questionPersistence.addQuestion(newQuestion);
        }
        return newQuestion;
    }

    public Question updateQuestion(String questionId, String question, String answer) {
        Question newQuestion = null;
        if (validateQuestionInputs(question, answer)) {
            newQuestion = new Question(questionId, question.trim(), answer.trim());
            questionPersistence.updateQuestion(newQuestion);
        }
        return newQuestion;
    }

    public boolean deleteQuestion(int currIndex, int size) {
        if (size > 0 && currIndex < questions.size() && currIndex >= 0) {
            System.out.println(questions.get(currIndex).getQuestionId());
            questionPersistence.deleteQuestionById(questions.get(currIndex).getQuestionId());
            return true;
        }
        return false;
    }

    @Override
    public List<Question> getQuestionsByTag(QuestionTag questionTag) {
        return questionPersistence.getQuestionsByTag(questionTag);
    }

    public QuestionTag addQuestionTag(Question question, QuestionTag questionTag) {
        QuestionTag newTag = questionPersistence.addQuestionTag(question, questionTag);
        return newTag;
    }

    private boolean validateQuestionInputs(String question, String answer) {
        String questionTrim = question.trim();
        String answerTrim = answer.trim();
        boolean validateLengths = validateQuestionInputLength(questionTrim) && validateQuestionInputLength(answerTrim);
        return !questionTrim.equals("") &&
                !answerTrim.equals("") &&
                validateLengths;
    }

    private boolean validateQuestionInputLength(String s) {
        return s.length() <= 250;
    }

    public Question getQuestionByIndex(int index) {
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;
    }

}

package comp3350.studyplus.logic;

import java.util.List;

import comp3350.studyplus.objects.Question;
import comp3350.studyplus.objects.QuestionTag;


public interface IQuestionHandler {

    List<Question> getQuestions();

    Question createQuestion(String question, String answer);

    Question updateQuestion(String questionId, String question, String answer);

    boolean deleteQuestion(int currIndex, int size);

    public QuestionTag addQuestionTag(Question question, QuestionTag questionTag);

    List<Question> getQuestionsByTag(QuestionTag questionTag);
    public Question getQuestionByIndex(int index);
}

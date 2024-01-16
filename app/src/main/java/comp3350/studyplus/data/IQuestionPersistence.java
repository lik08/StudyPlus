package comp3350.studyplus.data;
import java.util.*;

import comp3350.studyplus.objects.*;

public interface IQuestionPersistence {

    List<Question> getQuestionList();

    Question addQuestion(Question question);

    Question updateQuestion(Question updateQuestion);

    void deleteQuestionById(UUID questionId);

    QuestionTag addQuestionTag(Question question, QuestionTag questionTag);

    List<Question> getQuestionsByTag(QuestionTag targetTag);
}

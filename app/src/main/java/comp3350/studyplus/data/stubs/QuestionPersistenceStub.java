package comp3350.studyplus.data.stubs;

import java.util.*;

import comp3350.studyplus.objects.Question;
import comp3350.studyplus.data.IQuestionPersistence;
import comp3350.studyplus.objects.QuestionTag;

public class QuestionPersistenceStub implements IQuestionPersistence {
    private List<Question> questionList;

    public QuestionPersistenceStub() {
        this.questionList = new ArrayList<>();

        Question shortAnsQuestion1 = new Question("What does DNA stand for?", "Deoxyribonucleic Acid");
        this.addQuestion(shortAnsQuestion1);
        Question tFQuestion = new Question("2 is the only even prime number", "true");
        this.addQuestion(tFQuestion);
        Question shortAnsQuestion2 = new Question("How many bones are in the human body?", "206");
        this.addQuestion(shortAnsQuestion2);
    }

    @Override
    public List<Question> getQuestionList() {
        return questionList;
    }

    @Override
    public Question addQuestion(Question question) {
        int prevSize = questionList.size();
        questionList.add(question);

        if (prevSize >= questionList.size()) {
            return null;
        }

        return question;
    }

    @Override
    public Question updateQuestion(Question updatedQuestion) {
        return updatedQuestion;
    }

    @Override
    public void deleteQuestionById(UUID questionId) {
        for (int i = 0; i < questionList.size(); i ++) {
            if (questionList.get(i).getQuestionId().equals(questionId)) {
                questionList.remove(i);
                return;
            }
        }
    }

    // need this method due to interface - mocks in unit test
    @Override
    public QuestionTag addQuestionTag(Question question, QuestionTag questionTag) { return questionTag; }

    // need this method due to interface - mocks in unit test
    @Override
    public List<Question> getQuestionsByTag(QuestionTag targetTag) { return questionList; }

}

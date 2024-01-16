package comp3350.studyplus.logic;

import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.UUID;

import comp3350.studyplus.objects.Question;
import comp3350.studyplus.objects.QuestionTag;

public class DisplayQuestionTextIterator extends ViewModel {

    private IQuestionHandler questionHandler;
    private List<Question> questionList;
    private int currIndex;
    private boolean displayQuestion;
    QuestionTag questionTag;

    public DisplayQuestionTextIterator(QuestionTag tag) {
        questionHandler = new QuestionHandler();
        questionTag = tag;
        questionList = questionHandler.getQuestionsByTag(questionTag);
        displayQuestion = true;
        currIndex = 0;
    }

    private Question getCurrQuestion() {
        if(questionList.size() > 0) {
            return questionList.get(currIndex);
        }
        return null;
    }

    public String getCurrQuestionStr() {
        Question currQuestion = getCurrQuestion();
        if(currQuestion != null) {
            return currQuestion.getQuestion();
        }
        return null;
    }

    public String getCurrAnswerStr() {
        Question currQuestion = getCurrQuestion();
        if(currQuestion != null) {
            return currQuestion.getAnswer();
        }
        return null;
    }

    public UUID getCurrQuestionId() {
        return getCurrQuestion().getQuestionId();
    }

    public String flipDisplay(){

        displayQuestion = !displayQuestion;

        String result = setText();
        return result;
    }

    public String setText() {
        String result;
        if (questionList.size() == 0) {
            result = "Please add questions!";
        }
        else if (displayQuestion) {
            result = questionList.get(currIndex).getQuestion();
        }
        else {
            result = questionList.get(currIndex).getAnswer();
        }

        return result;
    }

    public List<Question> getQuestionList() { return questionList; }

    public String getNextQuestion() {
        if (currIndex < questionList.size()-1) {
            currIndex++;
        }
        else {
            currIndex = 0;
        }

        displayQuestion = true;
        String result = setText();
        return result;
    }

    public String getPrevQuestion() {
        if (currIndex == 0) {
            currIndex = questionList.size()-1;
        }
        else {
            currIndex--;
        }

        displayQuestion = true;
        String result = setText();
        return result;
    }

    public boolean deleteCurrentQuestion() {
        if(questionList.size() > 0) {
            return questionHandler.deleteQuestion(indexToQuestionListIndex(currIndex), questionList.size());
        }
        return false;
    }
    public int indexToQuestionListIndex(int index) {
        List<Question> allQuestions = questionHandler.getQuestions();
        for(int i = 0; i < allQuestions.size(); i ++) {
            if(questionList.get(index).getQuestionId().equals(allQuestions.get(i).getQuestionId())) {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty() {
        return questionList.isEmpty();
    }
}
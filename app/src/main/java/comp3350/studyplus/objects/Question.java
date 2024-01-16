package comp3350.studyplus.objects;

import java.util.*;
public class Question {
    private String question;
    private String answer;
    private UUID questionID;

    public Question(String questionId, String newQuestion, String newAns) {
        this.questionID = UUID.fromString(questionId);
        this.question = newQuestion;
        this.answer = newAns;
    }

    public Question(String newQuestion, String newAns) {
        this.questionID = UUID.randomUUID();
        this.question = newQuestion;
        this.answer = newAns;
    }

    public UUID getQuestionId() {
        return this.questionID;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getQuestion() {
        return this.question;
    }
}
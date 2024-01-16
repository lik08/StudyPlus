package com.studyplus.util;

import java.util.List;

import comp3350.studyplus.application.Services;
import comp3350.studyplus.data.IQuestionPersistence;
import comp3350.studyplus.data.hsqldb.QuestionPersistenceHSQLDB;
import comp3350.studyplus.logic.CourseHandler;
import comp3350.studyplus.logic.ICourseHandler;
import comp3350.studyplus.logic.IQuestionHandler;
import comp3350.studyplus.logic.QuestionHandler;
import comp3350.studyplus.objects.Course;
import comp3350.studyplus.objects.Question;
import comp3350.studyplus.objects.QuestionTag;

public class TestUtil {
    private IQuestionHandler questionHandler;
    private ICourseHandler courseHandler;
    private IQuestionPersistence questionPersistence;

    public TestUtil(){
        courseHandler = new CourseHandler();
        questionHandler = new QuestionHandler();
        questionPersistence = new QuestionPersistenceHSQLDB(Services.getQuestionPersistence().toString());
    }

    public Question getQuestionByPos(int index, Course course) {
        return questionHandler.getQuestionsByTag(course.getQuestionTag()).get(index);
    }

    public Course getCourseByPos(int index) {
        return courseHandler.getCourses().get(index);
    }

    public void insertQuestion(String question, String answer, Course course) {
        QuestionTag questionTag = courseHandler.getCourse(course.getCourseId()).getQuestionTag();
        Question newQuestion = questionHandler.createQuestion(question, answer);
        questionHandler.addQuestionTag(newQuestion, questionTag);
    }

    public int totalQuestion(){
        return questionHandler.getQuestions().size();
    }

    public void deleteQuestion(String question, String answer) {
        List<Question> questionList = questionHandler.getQuestions();
        for(int i = 0; i < questionList.size(); i++) {
            if(questionList.get(i).getQuestion().equals(question) && questionList.get(i).getAnswer().equals(answer)) {
                System.out.println(questionList.get(i).getQuestionId());
                questionHandler.deleteQuestion(i, questionList.size());
                break;
            }
        }
    }

    public void deleteCourse(String courseCode) {
        Course givenCourse = courseHandler.getCourse(courseCode);
        courseHandler.deleteCourse(givenCourse);
    }

    public int getIndexByCourseCode(String courseCode) {
        List<Course> coursesList = courseHandler.getCourses();
        for(int i = 0; i < coursesList.size(); i++) {
            if(coursesList.get(i).getCourseId().equals(courseCode)) {
                return i;
            }
        }
        return 0;
    }

    public void updateCourse(Course old, Course newCourse) {
        courseHandler.updateCourse(old, newCourse);
    }

    public int totalCourse() {
        return courseHandler.getCourses().size();
    }

    public void addCourse(Course newCourse) {
        courseHandler.addCourse(newCourse);
    }

    public List<Question> getQuestionsByTag(QuestionTag questionTag) {
        return questionHandler.getQuestionsByTag(questionTag);
    }

    public int getQuestionIndexFromCourse(Question question, Course course) {
        List<Question> courseQuestion = questionHandler.getQuestionsByTag(course.getQuestionTag());
        for(int i = 0; i < courseQuestion.size(); i++) {
            if (courseQuestion.get(i).getQuestion().equals(question.getQuestion())
                    && courseQuestion.get(i).getAnswer().equals(question.getAnswer())) {
                return i;
            }
        }
        return 0;
    }
}

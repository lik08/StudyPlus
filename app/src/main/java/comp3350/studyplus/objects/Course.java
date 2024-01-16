package comp3350.studyplus.objects;

import java.util.List;

public class Course {
    private final String courseId;
    private final String courseName;
    private QuestionTag questionTag;

    public Course(final String newId, final String newCourseName) {
        courseId = newId;
        courseName = newCourseName;
        questionTag = new QuestionTag(newId, newCourseName);
    }

    public Course(final String newId, final String newCourseName, QuestionTag newTag) {
        courseId = newId;
        courseName = newCourseName;
        questionTag = newTag;
    }

    public QuestionTag getQuestionTag() {
        return questionTag;
    }

    public String getCourseId()
    {
        return courseId;
    }

    public String getCourseName()
    {
        return courseName;
    }
}
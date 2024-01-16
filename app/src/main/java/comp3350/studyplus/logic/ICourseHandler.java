package comp3350.studyplus.logic;

import java.util.List;
import comp3350.studyplus.objects.Course;
import comp3350.studyplus.objects.QuestionTag;

public interface ICourseHandler {

    List<Course> getCourses();

    Course getCourse(String courseId);

    Course createCourse(String courseId, String courseName);

    Course createCourse(String courseId, String courseName, QuestionTag newTag);

    Course addCourse(Course currentCourse);

    Course updateCourse(Course oldCourse, Course newCourse);

    void deleteCourse(Course currentCourse);

    boolean ifCourseExists(Course newCourse);

    boolean ifNewCourseIdExists(Course oldCourse, Course newCourse);
}

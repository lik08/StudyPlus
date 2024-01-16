package comp3350.studyplus.logic;

import java.util.List;

import comp3350.studyplus.application.Services;
import comp3350.studyplus.objects.Course;
import comp3350.studyplus.data.ICoursePersistence;
import comp3350.studyplus.objects.QuestionTag;

public class CourseHandler implements ICourseHandler {
    private static final int MAX_LEN_INPUT = 100;
    private ICoursePersistence coursePersistence;
    private List<Course> courses;

    public CourseHandler() {
        this.coursePersistence = Services.getCoursePersistence();
        courses = getCourses();
    }

    public CourseHandler(final ICoursePersistence coursePersistence) {
        this.coursePersistence = coursePersistence;
    }

    public List<Course> getCourses() {
        courses = coursePersistence.getCoursesList();
        return courses;
    }

    public Course getCourse(String courseId) {
        courses = getCourses();
        Course course = null;
        boolean found = false;

        if (courses == null) {
            courses = coursePersistence.getCoursesList();
        }

        if (courses.size() > 0) {
            for (int i = 0; i < courses.size() && !found; i++) {
                if (courseId.equals(courses.get(i).getCourseId())) {
                    found = true;
                    course = courses.get(i);
                }
            }
        }

        return course;
    }

    public Course createCourse(String courseId, String courseName, QuestionTag tag) {
        Course newCourse = null;

        String cleanCourseId = cleanStr(courseId).toUpperCase();
        String cleanCourseName = cleanStr(courseName);
        if (validateStr(cleanCourseId) && validateStr(cleanCourseName)) {
            newCourse = new Course(cleanCourseId, cleanCourseName, tag);
        }
        return newCourse;
    }

    public Course createCourse(String courseId, String courseName) {
        Course newCourse = null;
        
        String cleanCourseId = cleanStr(courseId).toUpperCase();
        String cleanCourseName = cleanStr(courseName);
        if (validateStr(cleanCourseId) && validateStr(cleanCourseName)) {
            newCourse = new Course(cleanCourseId, cleanCourseName);
        }
        return newCourse;
    }

    private String cleanStr(String str) {
        String cleanStr = str.replaceAll("\\p{Punct}", "").trim();

        return cleanStr;
    }

    private boolean validateStr(String str) {
        return !str.equals("") && str.length() <= MAX_LEN_INPUT;
    }

    public Course addCourse(Course currentCourse)
    {
        return coursePersistence.addCourse(currentCourse);
    }

    public Course updateCourse(Course oldCourse, Course newCourse)
    {
        return coursePersistence.updateCourse(oldCourse, newCourse);
    }

    public void deleteCourse(Course currentCourse) {
        coursePersistence.deleteCourse(currentCourse);
    }

    public boolean ifCourseExists(Course newCourse) {
        return getCourse(newCourse.getCourseId()) != null;
    }


    public boolean ifNewCourseIdExists(Course oldCourse, Course newCourse) {
            return oldCourse != null
                    && newCourse != null
                    && (!(oldCourse.getCourseId().equals(newCourse.getCourseId()))
                    && getCourse(newCourse.getCourseId()) != null);
    }
}

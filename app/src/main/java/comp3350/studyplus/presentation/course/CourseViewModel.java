package comp3350.studyplus.presentation.course;

import androidx.lifecycle.ViewModel;

import java.util.List;

import comp3350.studyplus.logic.CourseHandler;
import comp3350.studyplus.logic.ICourseHandler;
import comp3350.studyplus.objects.Course;

public class CourseViewModel extends ViewModel {

    private List<Course> courses;
    private ICourseHandler courseHandler;

    public CourseViewModel() {
        courseHandler = new CourseHandler();
        courses = courseHandler.getCourses();
    }

    public List<Course> getCourses() {
        return courses;
    }
}
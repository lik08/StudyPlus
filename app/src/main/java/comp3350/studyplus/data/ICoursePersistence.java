package comp3350.studyplus.data;

import java.util.List;

import comp3350.studyplus.objects.Course;

public interface ICoursePersistence {
        Course addCourse(Course currentCourse);

        Course updateCourse(Course oldCourse, Course newCourse);

        void deleteCourse(Course currentCourse);

        List<Course> getCoursesList();
}

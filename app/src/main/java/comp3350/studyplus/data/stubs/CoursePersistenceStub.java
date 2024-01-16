package comp3350.studyplus.data.stubs;

import java.util.ArrayList;
import java.util.List;

import comp3350.studyplus.data.ICoursePersistence;
import comp3350.studyplus.objects.Course;

public class CoursePersistenceStub implements ICoursePersistence {

    private List<Course> courseList;

    public CoursePersistenceStub() {
        this.courseList = new ArrayList<>();

        this.courseList.add(new Course("COMP3350", "Software Engineering 1"));
        this.courseList.add(new Course("COMP3010", "Distributed Computing"));
        this.courseList.add(new Course("COMP3430", "Operating Systems"));
    }
    @Override
    public Course addCourse(Course currentCourse) {
        int prevSize = courseList.size();
        this.courseList.add(currentCourse);

        if (prevSize >= courseList.size()) {
            return null;
        }
        return currentCourse;
    }

    @Override
    public Course updateCourse(Course oldCourse, Course newCourse) {
        for (int i = 0; i < courseList.size(); i++) {
            if (courseList.get(i).getCourseId().equals(newCourse.getCourseId())) {
                courseList.set(i, newCourse);
                return newCourse;
            }
        }
        return null;
    }

    @Override
    public void deleteCourse(Course currentCourse) {
        int index = courseList.indexOf(currentCourse);

        if (index < 0) {
            return;
        }

        courseList.remove(index);
    }

    @Override
    public List<Course> getCoursesList() {
        return this.courseList;
    }
}

package com.studyplus;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.studyplus.util.TestUtil;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import comp3350.studyplus.MainActivity;
import comp3350.studyplus.R;
import comp3350.studyplus.objects.Course;
import comp3350.studyplus.objects.Question;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CoursesTest {
    private TestUtil testUtil;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void setUp(){
        //Tests named after UserStories
        testUtil = new TestUtil();
    }

    @Test
    public void testCreateCourses(){

        String courseCode = "TEST CODE";
        String courseName = "Test Name";
        onView(withId(R.id.nav_courses)).perform(click());

        //Create new course
        onView(withId(R.id.floatingAddCourseBtn)).perform(click());
        onView(withId(R.id.add_course_id_field)).perform(typeText(courseCode));
        onView(withId(R.id.add_course_name_field)).perform(typeText(courseName));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.add_course_btn)).perform(click());

        //Check that new course is in list and clicks it
        onView(ViewMatchers.withId(R.id.course_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(testUtil.getIndexByCourseCode(courseCode), click()));
        onView(withId(R.id.title_course)).check(matches(withText(courseName)));

        //Revert back
        testUtil.deleteCourse(courseCode);
    }

    @Test
    public void testUpdateCourses() {
        String courseCode = "COMP1010";
        String courseName = "Computer Science 1";
        Course oldCourse = testUtil.getCourseByPos(0);

        //Presses update button and enters new info
        onView(withId(R.id.nav_courses)).perform(click());
        onView(ViewMatchers.withId(R.id.course_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return null;
                    }
                    @Override
                    public String getDescription() {
                        return "";
                    }
                    @Override
                    public void perform(UiController uiController, View view) {
                        view.findViewById(R.id.update_course_btn).performClick();
                    }}));
        onView(withId(R.id.update_course_name_field)).perform(clearText(),typeText(courseName));
        onView(withId(R.id.update_course_btn)).perform(click());

        //Checks that new name is displayed
        onView(ViewMatchers.withId(R.id.course_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(testUtil.getIndexByCourseCode(courseCode),
                        click()));
        onView(withId(R.id.title_course)).check(matches(withText(courseName)));

        Course newCourse = testUtil.getCourseByPos(0);

        //Revert back
        testUtil.updateCourse(newCourse, oldCourse);
    }

    @Test
    public void testDisplayCourses() {
        onView(withId(R.id.nav_courses)).perform(click());
        for(int i = 0; i < testUtil.totalCourse(); i++) {
            onView(ViewMatchers.withId(R.id.course_list)).perform(RecyclerViewActions.scrollTo(
                    hasDescendant(withText(testUtil.getCourseByPos(i).getCourseId()))));
            onView(ViewMatchers.withId(R.id.course_list))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i,  click()));
            onView(withId(R.id.title_course)).check(matches(withText(testUtil.getCourseByPos(i).getCourseName())));
            pressBack();
        }
    }

    @Test
    public void testDeleteCourses() {

        //Deletes the course
        int courseIndex = testUtil.totalCourse()-1;
        Course deletedCourse = testUtil.getCourseByPos(courseIndex);
        List<Question> deletedQuestion = testUtil.getQuestionsByTag(deletedCourse.getQuestionTag());
        onView(withId(R.id.nav_courses)).perform(click());
        onView(ViewMatchers.withId(R.id.course_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(courseIndex,new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return null;
                    }
                    @Override
                    public String getDescription() {
                        return "";
                    }
                    @Override
                    public void perform(UiController uiController, View view) {
                        view.findViewById(R.id.delete_course_btn).performClick();
                    }}));

        //Checks all course ensuring its deleted
        for(int i = 0; i < testUtil.totalCourse(); i ++) {
            onView(ViewMatchers.withId(R.id.course_list))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(i,new ViewAction() {
                        @Override
                        public Matcher<View> getConstraints() {
                            return null;
                        }
                        @Override
                        public String getDescription() {
                            return "";
                        }
                        @Override
                        public void perform(UiController uiController, View view) {
                            Assert.assertFalse(view.findViewById(R.id.course_list_item_name).equals(deletedCourse.getCourseId()));
                        }}));
        }

        //Revert back
        testUtil.addCourse(deletedCourse);
        for(int i = 0; i < deletedQuestion.size(); i++) {
            testUtil.insertQuestion(deletedQuestion.get(i).getQuestion(), deletedQuestion.get(i).getAnswer(), deletedCourse);
        }
    }
}

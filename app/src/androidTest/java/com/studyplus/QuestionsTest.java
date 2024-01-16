package com.studyplus;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.studyplus.util.TestUtil;

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
public class QuestionsTest {
    private TestUtil testUtil;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void setUp(){
        //Tests named after UserStories
        testUtil = new TestUtil();
    }

    @Test
    public void testCreateCueCard() {
        int courseIndex = 0;
        String testQuestion = "Test Question";
        String testAnswer = "Test Answer";
        Course currCourse = testUtil.getCourseByPos(courseIndex);

        //Adds Question
        onView(withId(R.id.nav_courses)).perform(click());
        onView(ViewMatchers.withId(R.id.course_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(courseIndex, click()));

        onView(withId(R.id.add_question_btn)).perform(click());
        onView(withId(R.id.add_question_field)).perform(clearText(), typeText(testQuestion));
        onView(withId(R.id.add_answer_field)).perform(clearText(), typeText(testAnswer));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_question_submit)).perform(click());

        //Checks if Question is available
        int questionIndex = testUtil.getQuestionIndexFromCourse(new Question(testQuestion, testAnswer), currCourse);
        List<Question> courseQuestion = testUtil.getQuestionsByTag(currCourse.getQuestionTag());
        for(int i = 0; i < courseQuestion.size(); i++) {
            if(i == questionIndex) {
                onView(withId(R.id.cue_card_text)).check(matches(withText(testQuestion)));
                onView(withId(R.id.flip_btn)).perform(click());
                onView(withId(R.id.cue_card_text)).check(matches(withText(testAnswer)));
                break;
            }
            else {
                onView(withId(R.id.next_btn)).perform(click());
            }
        }

        //Revert back
        testUtil.deleteQuestion(testQuestion, testAnswer);

    }

    @Test
    public void testEditCueCard() {
        int index = 0;
        String testQuestion = "New Question";
        String testAnswer = "New Answer";
        Course currCourse = testUtil.getCourseByPos(index);
        Question oldQuestion = testUtil.getQuestionsByTag(currCourse.getQuestionTag()).get(index);

        //Edits Question
        onView(withId(R.id.nav_courses)).perform(click());
        onView(ViewMatchers.withId(R.id.course_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(index, click()));

        onView(withId(R.id.update_btn)).perform(click());
        onView(withId(R.id.update_question_field)).perform(clearText(), typeText(testQuestion));
        onView(withId(R.id.update_answer_field)).perform(clearText(), typeText(testAnswer));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.button_question_update)).perform(click());

        //Checks if Edited Question shows
        int questionIndex = testUtil.getQuestionIndexFromCourse(new Question(testQuestion, testAnswer), currCourse);
        List<Question> courseQuestion = testUtil.getQuestionsByTag(currCourse.getQuestionTag());
        for(int i = 0; i < courseQuestion.size(); i++) {
            if(i == questionIndex) {
                onView(withId(R.id.cue_card_text)).check(matches(withText(testQuestion)));
                onView(withId(R.id.flip_btn)).perform(click());
                onView(withId(R.id.cue_card_text)).check(matches(withText(testAnswer)));
                break;
            }
            else {
                onView(withId(R.id.next_btn)).perform(click());
            }
        }

        //Revert back
        testUtil.deleteQuestion(testQuestion, testAnswer);
        testUtil.insertQuestion(oldQuestion.getQuestion(), oldQuestion.getAnswer(), currCourse);
    }

    @Test
    public void testDeleteCueCard() {
        int index = 0;
        Course currCourse = testUtil.getCourseByPos(index);
        Question deleteQuestion = testUtil.getQuestionByPos(index, currCourse);

        //Deletes first Question
        onView(withId(R.id.nav_courses)).perform(click());
        onView(ViewMatchers.withId(R.id.course_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(index, click()));
        onView(withId(R.id.delete_btn)).perform(click());
        onView(withText("YES")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

        //Goes through all ensuring its deleted
        List<Question> courseQuestion = testUtil.getQuestionsByTag(currCourse.getQuestionTag());
        for(int i = 0; i < courseQuestion.size(); i++) {
            onView(withId(R.id.cue_card_text)).check(matches(not(withText(deleteQuestion.getQuestion()))));
            onView(withId(R.id.flip_btn)).perform(click());
            onView(withId(R.id.cue_card_text)).check(matches(not(withText(deleteQuestion.getAnswer()))));
            onView(withId(R.id.next_btn)).perform(click());
        }

        //Revert back
        testUtil.insertQuestion(deleteQuestion.getQuestion(), deleteQuestion.getAnswer(), currCourse);
    }

    @Test
    public void testViewCueCard() {
        int courseIndex = 0;
        Course course1 = testUtil.getCourseByPos(courseIndex);
        List<Question> course1Questions = testUtil.getQuestionsByTag(course1.getQuestionTag());

        onView(withId(R.id.nav_courses)).perform(click());
        onView(ViewMatchers.withId(R.id.course_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(courseIndex, click()));

        //Checks all Questions in DB are displayed
        for(int i = 0; i < course1Questions.size(); i++) {
            onView(withId(R.id.cue_card_text)).check(matches(withText(course1Questions.get(i).getQuestion())));
            onView(withId(R.id.flip_btn)).perform(click());
            onView(withId(R.id.cue_card_text)).check(matches(withText(course1Questions.get(i).getAnswer())));
            onView(withId(R.id.next_btn)).perform(click());
        }
    }
}

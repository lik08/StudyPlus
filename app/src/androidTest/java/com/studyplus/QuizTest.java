package com.studyplus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.studyplus.util.TestUtil;

import comp3350.studyplus.MainActivity;
import comp3350.studyplus.R;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class QuizTest {

    private TestUtil testUtil;

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void setUp(){
        //Tests named after UserStories
        testUtil = new TestUtil();
    }


    @Test
    public void testDisplayQuiz() {
        onView(withId(R.id.nav_quiz_setting)).perform(click());
        onView(withId(R.id.total_question_select)).perform(typeText("" + testUtil.totalQuestion()));
        onView(withId(R.id.time_per_question)).perform(typeText("10"));
        onView(withId(R.id.start_button)).perform(click());
        //Answer all questions
        for(int i = 0; i < testUtil.totalQuestion(); i++) {
            onView(withId(R.id.input_answer)).perform(typeText("Test"));
            Espresso.closeSoftKeyboard();
            onView(withId(R.id.submit_btn)).perform(click());
        }

        onView(withText("Total Correct: 0/"
                + testUtil.totalQuestion() + "\nFinal Score: 0%")).inRoot(isDialog()).check(matches(isDisplayed()));
        onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

    }
}

package com.studyplus;


import androidx.test.filters.LargeTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@LargeTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        QuizTest.class,
        QuestionsTest.class,
        CoursesTest.class
})

public class AllAcceptanceTests {
}

package comp3350.studyplus;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.studyplus.logic.CourseHandlerTest;
import comp3350.studyplus.logic.QuestionHandlerTest;
import comp3350.studyplus.logic.QuizHandlerTest;
import comp3350.studyplus.objects.QuestionTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        QuestionTest.class,
        QuestionHandlerTest.class,
        CourseHandlerTest.class,
        QuizHandlerTest.class
})
public class AllUnitTests {
}

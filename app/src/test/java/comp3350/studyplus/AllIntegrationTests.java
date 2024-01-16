package comp3350.studyplus;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import comp3350.studyplus.logic.CourseHandlerIT;
import comp3350.studyplus.logic.QuestionHandlerIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        QuestionHandlerIT.class,
        CourseHandlerIT.class
})
public class AllIntegrationTests {
}

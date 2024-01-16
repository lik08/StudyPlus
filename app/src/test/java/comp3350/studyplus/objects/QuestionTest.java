package comp3350.studyplus.objects;

//imports
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.UUID;

import comp3350.studyplus.objects.Question;

/**
 * Test the Question object
 */
public class QuestionTest {

    /**
     * Inform that the unit test suite is starting
     */
    @BeforeClass
    public static void setup() {
        System.out.println("\nStarting tests for Question Object");
    }

    /**
     * Test the creation of Question
     */
    @Test
    public void testInstantiateQuestion() {
        String questionOneId = UUID.randomUUID().toString();
        Question questionOne = new Question(questionOneId, "What is the powerhouse " +
                "of the cell?", "Mitochondria");
        String questionTwoId = UUID.randomUUID().toString();
        Question questionTwo = new Question(questionTwoId, "", "");

        //assertions - regular case
        assertNotNull(questionOne);
        assertEquals(questionOneId, questionOne.getQuestionId().toString());
        assertEquals("What is the powerhouse of the cell?",
                questionOne.getQuestion());
        assertEquals("Mitochondria", questionOne.getAnswer());

        //assertions - edge case
        assertNotNull(questionTwo);
        assertEquals(questionTwoId, questionTwo.getQuestionId().toString());
        assertEquals("", questionTwo.getQuestion());
        assertEquals("", questionTwo.getAnswer());

        System.out.println("Finished testing for instantiating Question");
    }

    /**
     * Inform that the tests have finished
     */
    @AfterClass
    public static void finish() {
        System.out.println("Finished tests for Question object");
    }
}

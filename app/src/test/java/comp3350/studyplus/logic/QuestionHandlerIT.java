package comp3350.studyplus.logic;

//imports

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.studyplus.data.IQuestionPersistence;
import comp3350.studyplus.data.hsqldb.QuestionPersistenceHSQLDB;
import comp3350.studyplus.objects.Question;
import comp3350.studyplus.objects.QuestionTag;
import comp3350.studyplus.util.TestUtils;

/**
 * Test the QuestionHandler class
 */
public class QuestionHandlerIT {

    private static IQuestionHandler handler;

    private File tempDb;

    /**
     * Inform that the unit test suite is starting
     */
    @BeforeClass
    public static void start() {
        System.out.println("\nStarting integration tests for QuestionHandler");
    }

    @Before
    public void setUp() throws IOException {
        this.tempDb = TestUtils.copyDB();
        final IQuestionPersistence persistence = new QuestionPersistenceHSQLDB(this.tempDb.getAbsolutePath().replace(".script", ""));
        this.handler = new QuestionHandler(persistence);
        assertNotNull(this.handler);
    }

    /**
     * Test the successful createQuestion method
     */
    @Test
    public void testCreateQuestion_Success() {
        System.out.println("Testing createQuestion - Success");

        List<Question> questionList = handler.getQuestions();
        int expectedListSize = questionList.size() + 1;

        assertNotNull(handler.createQuestion("What is the 3rd planet from the sun?", "Earth"));
        assertEquals(expectedListSize, questionList.size());

        System.out.println("Finished testing createQuestion - Success");
    }

    @Test
    public void testCreateQuestion_SuccessWithMaxCharacters() {
        System.out.println("Testing createQuestion - Success");

        List<Question> questionList = handler.getQuestions();
        int expectedListSize = questionList.size() + 1;

        //exactly 250 characters
        assertNotNull(handler.createQuestion("1                                                                                                                                                                                                                                                        1",
                "1                                                                                                                                                                                                                                                        1"));
        assertEquals(expectedListSize, questionList.size());

        System.out.println("Finished testing createQuestion - Success");
    }

    /**
     * Test the failure createQuestion method
     */
    @Test
    public void testCreateQuestion_FailureEmptyString() {
        System.out.println("Testing createQuestion - Failure (Empty string)");

        List<Question> questionList = handler.getQuestions();
        int listSizeBeforeCreateQuestion = questionList.size();

        assertNull(handler.createQuestion("", "1"));
        assertNull(handler.createQuestion("1", ""));
        assertNull(handler.createQuestion("", ""));
        assertEquals(listSizeBeforeCreateQuestion, questionList.size());

        System.out.println("Finished testing createQuestion - Failure (Empty string");
    }

    @Test
    public void testCreateQuestion_FailureWhitespace() {
        System.out.println("Testing createQuestion - Failure (Whitespace)");

        List<Question> questionList = handler.getQuestions();
        int listSizeBeforeCreateQuestion = questionList.size();

        assertNull(handler.createQuestion("   ", "1"));
        assertNull(handler.createQuestion("1", "   "));
        assertNull(handler.createQuestion("        ", "          "));
        assertEquals(listSizeBeforeCreateQuestion, questionList.size());

        System.out.println("Finished testing createQuestion - Failure (Whitespace");
    }

    @Test
    public void testCreateQuestion_FailureWithMoreThanMaxCharacters() {
        System.out.println("Testing createQuestion - Failure (Max Characters)");

        List<Question> questionList = handler.getQuestions();
        int listSizeBeforeCreateQuestion = questionList.size();

        //some story generated from chatgpt
        assertNull(handler.createQuestion("1",
                "Once upon a time, a shy mouse named Mia discovered a hidden treasure map. With courage in her heart, she embarked on a daring adventure, facing fearsome creatures and treacherous paths. In the end, she found the treasure, proving that even the smallest can achieve greatness."));
        assertNull(handler.createQuestion("Once upon a time, a shy mouse named Mia discovered a hidden treasure map. With courage in her heart, she embarked on a daring adventure, facing fearsome creatures and treacherous paths. In the end, she found the treasure, proving that even the smallest can achieve greatness.",
                "1"));
        assertNull(handler.createQuestion("Once upon a time, a shy mouse named Mia discovered a hidden treasure map. With courage in her heart, she embarked on a daring adventure, facing fearsome creatures and treacherous paths. In the end, she found the treasure, proving that even the smallest can achieve greatness.",
                "1"));
        assertEquals(listSizeBeforeCreateQuestion, questionList.size());

        System.out.println("Finished testing createQuestion - Failure (Max Characters)");
    }

    /**
     * Test the successful deleteQuestion method
     */
    @Test
    public void testDeleteQuestion_Success(){
        System.out.println("Testing deleteQuestion - Success");

        List<Question> questionList = handler.getQuestions();
        int expectedListSize = questionList.size() - 1;

        assertTrue(handler.deleteQuestion(1, questionList.size()));
        assertEquals(expectedListSize, questionList.size());

        System.out.println("Finished testing deleteQuestion - Success");
    }

    /**
     * Test the failure deleteQuestion method
     */
    @Test
    public void testDeleteQuestion_Failure(){
        System.out.println("Testing deleteQuestion - Failure");

        List<Question> questionList = handler.getQuestions();
        int expectedListSize = questionList.size();

        //Invalid index negative
        assertFalse(handler.deleteQuestion(-1, questionList.size()));
        assertEquals(expectedListSize, questionList.size());

        //Invalid index out of bound
        assertFalse(handler.deleteQuestion(Integer.MAX_VALUE, questionList.size()));
        assertEquals(expectedListSize, questionList.size());


        //Remove rest of questions
        int listSize = questionList.size();
        for (int i = 0; i < listSize; i++) {
            handler.deleteQuestion(0, questionList.size());
        }

        //Delete works with empty list
        assertFalse(handler.deleteQuestion(0, questionList.size()));
        assertEquals(0,questionList.size());

        System.out.println("Finished testing deleteQuestion - Failure");
    }

    @Test
    public void testGetQuestionsByTag() {
        System.out.println("Testing getQuestionsByTag");

        QuestionTag tag = new QuestionTag("BIO1010", "ANYTHING");
        assertNotNull(handler.getQuestionsByTag(tag));

        System.out.println("Finished testing getQuestionsByTag");
    }

    /**
     * Test the successful updateQuestion method
     */
    @Test
    public void testUpdateQuestion_Success() {
        System.out.println("Testing updateQuestion - Success");

        List<Question> questionList = handler.getQuestions();
        String questionId = questionList.get(0).getQuestionId().toString();

        assertNotNull(handler.updateQuestion(questionId, "What is the 3rd planet from the sun?", "Earth"));

        System.out.println("Finished testing updateQuestion - Success");
    }

    @Test
    public void testUpdateQuestion_SuccessWithMaxCharacters() {
        System.out.println("Testing updateQuestion - Success");

        List<Question> questionList = handler.getQuestions();
        String questionId = questionList.get(0).getQuestionId().toString();

        //exactly 250 characters
        assertNotNull(handler.updateQuestion(questionId,"1                                                                                                                                                                                                                                                        1",
                "1                                                                                                                                                                                                                                                        1"));

        System.out.println("Finished testing updateQuestion - Success");
    }

    /**
     * Test the failure updateQuestion method
     */
    @Test
    public void testUpdateQuestion_FailureEmptyString() {
        System.out.println("Testing updateQuestion - Failure (Empty string)");

        List<Question> questionList = handler.getQuestions();
        String questionId = questionList.get(0).getQuestionId().toString();

        assertNull(handler.updateQuestion(questionId, "", "1"));
        assertNull(handler.updateQuestion(questionId, "1", ""));
        assertNull(handler.updateQuestion(questionId, "", ""));

        System.out.println("Finished testing updateQuestion - Failure (Empty string");
    }

    @Test
    public void testUpdateQuestion_FailureWhitespace() {
        System.out.println("Testing updateQuestion - Failure (Whitespace)");

        List<Question> questionList = handler.getQuestions();
        String questionId = questionList.get(0).getQuestionId().toString();

        assertNull(handler.updateQuestion(questionId, "   ", "1"));
        assertNull(handler.updateQuestion(questionId, "1", "   "));
        assertNull(handler.updateQuestion(questionId, "        ", "          "));

        System.out.println("Finished testing updateQuestion - Failure (Whitespace");
    }

    @Test
    public void testUpdateQuestion_FailureWithMoreThanMaxCharacters() {
        System.out.println("Testing UpdateQuestion - Failure (Max Characters)");

        List<Question> questionList = handler.getQuestions();
        String questionId = questionList.get(0).getQuestionId().toString();

        //some story generated from chatgpt
        assertNull(handler.updateQuestion(questionId, "1",
                "Once upon a time, a shy mouse named Mia discovered a hidden treasure map. With courage in her heart, she embarked on a daring adventure, facing fearsome creatures and treacherous paths. In the end, she found the treasure, proving that even the smallest can achieve greatness."));
        assertNull(handler.updateQuestion(questionId, "Once upon a time, a shy mouse named Mia discovered a hidden treasure map. With courage in her heart, she embarked on a daring adventure, facing fearsome creatures and treacherous paths. In the end, she found the treasure, proving that even the smallest can achieve greatness.",
                "1"));

        System.out.println("Finished testing updateQuestion - Failure (Max Characters)");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDb.delete();
    }

    /**
     * Inform that the test has finished
     */
    @AfterClass
    public static void finish() { System.out.println("Finished tests for QuestionHandler");
    }
}

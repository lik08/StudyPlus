package comp3350.studyplus.logic;

//imports
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import comp3350.studyplus.data.IQuestionPersistence;
import comp3350.studyplus.data.stubs.QuestionPersistenceStub;
import comp3350.studyplus.objects.Question;
import comp3350.studyplus.objects.QuestionTag;


/**
 * Test the QuestionHandler class
 */
public class QuestionHandlerTest {

    private final int NUM_QUESTIONS = 3;
    private static IQuestionHandler handler;

    /**
     * Inform that the unit test suite is starting
     */
    @BeforeClass
    public static void start() {
        System.out.println("\nStarting tests for QuestionHandler");
    }

    @Before
    public void setUp() {
        handler = new QuestionHandler(new QuestionPersistenceStub());
        assertNotNull(handler);
    }

    /**
     * Test the getQuestions method
     */
    @Test
    public void testGetQuestions() {
        System.out.println("Testing getQuestions");

        List<Question> questionList = handler.getQuestions();
        assertNotNull(questionList);

        String[] questions = {"What does DNA stand for?", "2 is the only even prime number", "How many bones are in the human body?"};
        String[] answers = {"Deoxyribonucleic Acid", "true", "206"};

        for (int i = 0; i < NUM_QUESTIONS; i++) {
            assertEquals(questions[i], questionList.get(i).getQuestion());
            assertEquals(answers[i], questionList.get(i).getAnswer());
        }

        System.out.println("Finished testing getQuestions");
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

        //Deletes all questions
        assertTrue(handler.deleteQuestion(1, questionList.size()));
        assertTrue(handler.deleteQuestion(0, questionList.size()));
        assertEquals(0, questionList.size());

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
    public void testGetQuestionTag() {
        System.out.println("Testing getQuestionTag");

        QuestionTag tag = new QuestionTag("COURSE","ANYTHING");
        Question newQuestion = new Question("dummy", "dummy");

        IQuestionPersistence mockPersistence = mock(IQuestionPersistence.class);
        IQuestionHandler questionHandler = new QuestionHandler(mockPersistence);
        when(mockPersistence.addQuestionTag(newQuestion, tag)).thenReturn(tag);

        QuestionTag newTag = questionHandler.addQuestionTag(newQuestion, tag);
        assertNotNull(newTag);
        assertEquals(tag.getTagId(), newTag.getTagId());

        System.out.println("Finished testing getQuestionTag");
    }

    @Test
    public void testGetQuestionsByTag() {
        System.out.println("Testing getQuestionsByTag");

        QuestionTag tag = new QuestionTag("COURSE", "ANYTHING");
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("1", "2"));
        questions.add(new Question("3", "4"));
        questions.add(new Question("5", "6"));

        IQuestionPersistence mockPersistence = mock(IQuestionPersistence.class);
        IQuestionHandler questionHandler = new QuestionHandler(mockPersistence);

        when(mockPersistence.getQuestionsByTag(tag)).thenReturn(questions);

        assertNotNull(questionHandler.getQuestionsByTag(tag));

        System.out.println("Finished testing getQuestionsByTag");
    }

    /**
     * Test the successful updateQuestion method
     */
    @Test
    public void testUpdateQuestion_Success() {
        System.out.println("Testing updateQuestion - Success");

        Question newQuestion = new Question("e019ba07-530f-4c73-9156-1cbe6a944310", "What is the 3rd planet from the sun?", "Earth");

        IQuestionPersistence mockPersistence = mock(IQuestionPersistence.class);
        IQuestionHandler questionHandler = new QuestionHandler(mockPersistence);

        when(mockPersistence.updateQuestion(newQuestion)).thenReturn(newQuestion);

        assertNotNull(questionHandler.updateQuestion("e019ba07-530f-4c73-9156-1cbe6a944310", "What is the 3rd planet from the sun?", "Earth"));

        System.out.println("Finished testing updateQuestion - Success");
    }

    @Test
    public void testUpdateQuestion_SuccessWithMaxCharacters() {
        System.out.println("Testing updateQuestion - Success");

        Question newQuestion = new Question("e019ba07-530f-4c73-9156-1cbe6a944310",
                "1                                                                                                                                                                                                                                                        1",
                "1                                                                                                                                                                                                                                                        1");

        IQuestionPersistence mockPersistence = mock(IQuestionPersistence.class);
        IQuestionHandler questionHandler = new QuestionHandler(mockPersistence);

        when(mockPersistence.updateQuestion(newQuestion)).thenReturn(newQuestion);

        assertNotNull(questionHandler.updateQuestion("e019ba07-530f-4c73-9156-1cbe6a944310", "1                                                                                                                                                                                                                                                        1",
                "1                                                                                                                                                                                                                                                        1"));

        System.out.println("Finished testing updateQuestion - Success");
    }

    /**
     * Test the failure updateQuestion method
     */
    @Test
    public void testUpdateQuestion_FailureEmptyString() {
        System.out.println("Testing updateQuestion - Failure (Empty string)");

        IQuestionPersistence mockPersistence = mock(IQuestionPersistence.class);
        IQuestionHandler questionHandler = new QuestionHandler(mockPersistence);

        Question questionOne = new Question("e019ba07-530f-4c73-9156-1cbe6a944310", "", "1");
        when(mockPersistence.updateQuestion(questionOne)).thenReturn(questionOne);
        assertNull(questionHandler.updateQuestion("e019ba07-530f-4c73-9156-1cbe6a944310", "", "1"));

        Question questionTwo = new Question("b1225cd4-d64b-44d1-bec4-deaf62656d40", "1", "");
        when(mockPersistence.updateQuestion(questionTwo)).thenReturn(questionTwo);
        assertNull(questionHandler.updateQuestion("b1225cd4-d64b-44d1-bec4-deaf62656d40", "1", ""));

        Question questionThree = new Question("fe1b0d67-90be-44f5-88a7-453e391af27c", "", "");
        when(mockPersistence.updateQuestion(questionThree)).thenReturn(questionThree);
        assertNull(questionHandler.updateQuestion("fe1b0d67-90be-44f5-88a7-453e391af27c", "1", ""));

        System.out.println("Finished testing updateQuestion - Failure (Empty string)");
    }

    @Test
    public void testUpdateQuestion_FailureWhitespace() {
        System.out.println("Testing updateQuestion - Failure (Whitespace)");

        IQuestionPersistence mockPersistence = mock(IQuestionPersistence.class);
        IQuestionHandler questionHandler = new QuestionHandler(mockPersistence);

        Question questionOne = new Question("e019ba07-530f-4c73-9156-1cbe6a944310", "   ", "1");
        when(mockPersistence.updateQuestion(questionOne)).thenReturn(questionOne);
        assertNull(questionHandler.updateQuestion("e019ba07-530f-4c73-9156-1cbe6a944310", "   ", "1"));

        Question questionTwo = new Question("b1225cd4-d64b-44d1-bec4-deaf62656d40", "1", "   ");
        when(mockPersistence.updateQuestion(questionTwo)).thenReturn(questionTwo);
        assertNull(questionHandler.updateQuestion("b1225cd4-d64b-44d1-bec4-deaf62656d40", "1", "   "));

        Question questionThree = new Question("fe1b0d67-90be-44f5-88a7-453e391af27c", "        ", "          ");
        when(mockPersistence.updateQuestion(questionThree)).thenReturn(questionThree);
        assertNull(questionHandler.updateQuestion("fe1b0d67-90be-44f5-88a7-453e391af27c", "        ", "          "));

        System.out.println("Finished testing updateQuestion - Failure (Whitespace");
    }

    @Test
    public void testUpdateQuestion_FailureWithMoreThanMaxCharacters() {
        System.out.println("Testing UpdateQuestion - Failure (Max Characters)");

        IQuestionPersistence mockPersistence = mock(IQuestionPersistence.class);
        IQuestionHandler questionHandler = new QuestionHandler(mockPersistence);

        Question questionOne = new Question("e019ba07-530f-4c73-9156-1cbe6a944310", "1",
                "Once upon a time, a shy mouse named Mia discovered a hidden treasure map. With courage in her heart, she embarked on a daring adventure, facing fearsome creatures and treacherous paths. In the end, she found the treasure, proving that even the smallest can achieve greatness.");
        when(mockPersistence.updateQuestion(questionOne)).thenReturn(questionOne);
        assertNull(questionHandler.updateQuestion("e019ba07-530f-4c73-9156-1cbe6a944310", "1",
                "Once upon a time, a shy mouse named Mia discovered a hidden treasure map. With courage in her heart, she embarked on a daring adventure, facing fearsome creatures and treacherous paths. In the end, she found the treasure, proving that even the smallest can achieve greatness."));

        Question questionTwo = new Question("b1225cd4-d64b-44d1-bec4-deaf62656d40", "Once upon a time, a shy mouse named Mia discovered a hidden treasure map. With courage in her heart, she embarked on a daring adventure, facing fearsome creatures and treacherous paths. In the end, she found the treasure, proving that even the smallest can achieve greatness.",
                "1");
        when(mockPersistence.updateQuestion(questionTwo)).thenReturn(questionTwo);
        assertNull(questionHandler.updateQuestion("b1225cd4-d64b-44d1-bec4-deaf62656d40", "Once upon a time, a shy mouse named Mia discovered a hidden treasure map. With courage in her heart, she embarked on a daring adventure, facing fearsome creatures and treacherous paths. In the end, she found the treasure, proving that even the smallest can achieve greatness.",
                "1"));


        System.out.println("Finished testing updateQuestion - Failure (Max Characters)");
    }

    /**
     * Inform that the test has finished
     */
    @AfterClass
    public static void finish() { System.out.println("Finished tests for QuestionHandler");
    }
}

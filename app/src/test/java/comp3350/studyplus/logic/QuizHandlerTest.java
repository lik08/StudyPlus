package comp3350.studyplus.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;


import comp3350.studyplus.data.stubs.QuestionPersistenceStub;
import comp3350.studyplus.objects.Question;

public class QuizHandlerTest {
    public static IQuizHandler quizHandler;

    @BeforeClass
    public static void start() {
        System.out.println("\nStarting tests for QuizHandler");
    }

    @Before
    public void setUp() {
        List<Question> questionList = new ArrayList<Question>();
        questionList.add(new Question("Question1", "Answer1"));
        questionList.add(new Question("Question2", "Answer2"));
        questionList.add(new Question("Question3", "Answer3"));
        quizHandler = new QuizHandler(questionList, 3);
    }

    @Test
    public void testValidateAnswer_Success() {
        System.out.println("Testing validateAnswer - Success");

        assertTrue(quizHandler.getNextQuestion());
        assertTrue(quizHandler.validateAnswer(quizHandler.getCurrentQuestion().getAnswer()));
        assertEquals(quizHandler.getCorrectAnswers(), 1);

        assertTrue(quizHandler.validateAnswer(quizHandler.getCurrentQuestion().getAnswer().toLowerCase()));
        assertEquals(quizHandler.getCorrectAnswers(), 2);

        assertTrue(quizHandler.validateAnswer(quizHandler.getCurrentQuestion().getAnswer().toUpperCase()));
        assertEquals(quizHandler.getCorrectAnswers(), 3);

        System.out.println("Finished testing validateAnswer - Success");
    }

    @Test
    public void testValidateAnswer_Failure() {
        System.out.println("Testing validateAnswer - Failure");
        String testAnswer = "Answer";

        assertTrue(quizHandler.getNextQuestion());
        assertFalse(quizHandler.validateAnswer(testAnswer));
        assertEquals(quizHandler.getCorrectAnswers(), 0);

        assertFalse(quizHandler.validateAnswer(testAnswer.toLowerCase()));
        assertEquals(quizHandler.getCorrectAnswers(), 0);

        assertFalse(quizHandler.validateAnswer(testAnswer.toUpperCase()));
        assertEquals(quizHandler.getCorrectAnswers(), 0);


        System.out.println("Finished testing validateAnswer - Failure");
    }

    @Test
    public void testGetNextQuestion() {
        System.out.println("Testing getNextQuestion");

        //Gets first 3 questions
        assertTrue(quizHandler.getNextQuestion());
        assertEquals(quizHandler.getCurrentQuestionIndex(), 1);
        assertNotNull(quizHandler.getCurrentQuestion());

        assertTrue(quizHandler.getNextQuestion());
        assertEquals(quizHandler.getCurrentQuestionIndex(), 2);
        assertNotNull(quizHandler.getCurrentQuestion());

        assertTrue(quizHandler.getNextQuestion());
        assertEquals(quizHandler.getCurrentQuestionIndex(), 3);
        assertNotNull(quizHandler.getCurrentQuestion());

        //No more questions
        assertFalse(quizHandler.getNextQuestion());
        assertEquals(quizHandler.getCurrentQuestionIndex(), 3);
        assertNull(quizHandler.getCurrentQuestion());

        assertFalse(quizHandler.getNextQuestion());
        assertEquals(quizHandler.getCurrentQuestionIndex(), 3);
        assertNull(quizHandler.getCurrentQuestion());

        System.out.println("Finished testing getNextQuestion");
    }

    @Test
    public void testGetTotalQuestions() {
        // T E S T   C O V E R A G E
        System.out.println("Testing getTotalQuestions");

        assertNotNull(quizHandler.getTotalQuestion());

        System.out.println("Finished testing getTotalQuestions");
    }

    @AfterClass
    public static void finish() { System.out.println("Finished tests for QuizHandler");
    }

}

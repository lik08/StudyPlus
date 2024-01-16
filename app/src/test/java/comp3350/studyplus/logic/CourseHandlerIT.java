package comp3350.studyplus.logic;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import comp3350.studyplus.data.ICoursePersistence;
import comp3350.studyplus.data.hsqldb.CoursePersistenceHSQLDB;
import comp3350.studyplus.objects.Course;
import comp3350.studyplus.objects.QuestionTag;
import comp3350.studyplus.util.TestUtils;

public class CourseHandlerIT {
    private static ICourseHandler handler;

    private File tempDb;

    @BeforeClass
    public static void start() {System.out.println("\nStarting tests for CourseHandler"); }

    @Before
    public void setUp() throws IOException{
        this.tempDb = TestUtils.copyDB();
        final ICoursePersistence persistence = new CoursePersistenceHSQLDB(this.tempDb.getAbsolutePath().replace(".script", ""));
        this.handler = new CourseHandler(persistence);
        assertNotNull(this.handler);
    }

    @Test
    public void testGetCourse_Success() {
        System.out.println("Testing getCourse - Success");

        Course course = new Course("COMP3010", "Distributed Computing");
        handler.addCourse(course);

        Course actualCourse = handler.getCourse("COMP3010");

        assertEquals("COMP3010", actualCourse.getCourseId());
        assertEquals("Distributed Computing", actualCourse.getCourseName());

        System.out.println("Finished testing getCourse - Success");
    }

    @Test
    public void testGetCourse_FailureCourseNotFound() {
        System.out.println("Testing getCourse - Failure Course Not Found");

        assertNull(handler.getCourse("FAKE1010"));

        System.out.println("Finished Testing getCourse - Failure Course Not Found");
    }

    @Test
    public void testAddCourse_Success() {
        System.out.println("Testing addCourse - Success");

        List<Course> courseList = handler.getCourses();
        int expected = courseList.size() + 1;

        handler.addCourse(new Course("COMP1020", "Introduction to Computer Science 2"));

        assertEquals(expected, courseList.size());
        System.out.println("Finished Testing addCourse - Success");
    }

    @Test
    public void testIfCourseExists_True() {
        System.out.println("Testing ifCourseExists - True");

        Course newCourse = new Course("TEST2000", "NewTestingCourse");
        handler.addCourse(newCourse);

        assertTrue(handler.ifCourseExists(newCourse));

        System.out.println("Testing ifCourseExists - True");
    }

    @Test
    public void testIfCourseExists_False() {
        System.out.println("Testing ifCourseExists - False");

        Course newCourse = new Course("TEST3000", "NewTestingCourse");

        assertFalse(handler.ifCourseExists(newCourse));

        System.out.println("Testing ifCourseExists - False");
    }

    @Test
    public void testIfNewCourseExists_TrueSameCourseIdAsOtherCourse() {
        System.out.println("Testing IfNewCourseExists - True Same Course Id As Existing Course");

        Course existingCourse = new Course("TEST6000", "NewTestingCourse");
        handler.addCourse(existingCourse);
        Course course = new Course("COMP3350", "NewTestingCourse");

        assertTrue(handler.ifNewCourseIdExists(course, existingCourse));

        System.out.println("Finished Testing IfNewCourseExists - True Same Course Id As Existing Course");
    }

    @Test
    public void testIfNewCourseExists_FalseDifferentCourseId() {
        System.out.println("Testing IfNewCourseExists - False Different Course Id");

        Course oldCourse = new Course("TEST7000", "OldTestingCourse");
        handler.addCourse(oldCourse);
        Course newCourse = new Course("TEST7001", "OldTestingCourse");

        assertFalse(handler.ifNewCourseIdExists(oldCourse, newCourse));

        System.out.println("Finished Testing IfNewCourseExists - False Different Course Id");
    }

    @Test
    public void testIfNewCourseExists_FalseSameCourseId() {
        System.out.println("Testing IfNewCourseExists - False Same Course Id");

        Course oldCourse = new Course("TEST7000", "OldTestingCourse");
        handler.addCourse(oldCourse);
        Course newCourse = new Course("TEST7000", "NewTestingCourse");

        assertFalse(handler.ifNewCourseIdExists(oldCourse, newCourse));

        System.out.println("Finished Testing IfNewCourseExists - False Same Course Id");
    }


    @Test
    public void testCreateCourse_Success() {
        System.out.println("Testing createCourse - Success");

        Course newCourse = handler.createCourse("TEST10000", "NewTestingCourse");

        assertNotNull(newCourse);

        System.out.println("Testing createCourse - Success");
    }

    @Test
    public void testCreateCourseWithTag_Success() {
        System.out.println("Testing createCourse - Success");

        Course newCourse = handler.createCourse("TEST10000", "NewTestingCourse", new QuestionTag("COURSE","TAG"));

        assertNotNull(newCourse);

        System.out.println("Finished Testing createCourse - Success");
    }

    @Test
    public void testCreateCourse_FailureEmptyAndWhiteSpace() {
        System.out.println("Testing createCourse - Failure Empty And White Space");

        assertNull(handler.createCourse("NONAME", ""));
        assertNull(handler.createCourse("", "NOID"));
        assertNull(handler.createCourse("",""));
        assertNull(handler.createCourse("WHITE SPACE","    "));
        assertNull(handler.createCourse("       ","WHITE SPACE"));
        assertNull(handler.createCourse("       ", "         "));

        System.out.println("Finished testing createCourse - Failure Empty And White Space");
    }

    @Test
    public void testCreateCourse_FailureMaxLength() {
        System.out.println("Testing createCourse - Failure Max Length");
        // max length is currently 100 characters

        assertNull(handler.createCourse("01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567891", "101 Characters"));
        assertNull(handler.createCourse("101 Characters", "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567891"));
        assertNull(handler.createCourse("01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567891", "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567891"));

        System.out.println("Finished testing createCourse - Failure Max Length");
    }


    @Test
    public void testCreateCourse_FailurePunctuationOnly() {
        System.out.println("Testing createCourse - Failure Punctuation Only");

        Course newCourse = handler.createCourse("!!!!!!", "NewTestingCourse");

        assertNull(newCourse);

        System.out.println("Testing createCourse - Failure Punctuation Only");
    }

    @Test
    public void testUpdateCourse_Success() {
        System.out.println("Testing updateCourse - Success");

        List<Course> courseList = handler.getCourses();

        Course oldCourse = new Course("TEST1000", "OldTestingCourse");
        courseList.add(oldCourse);
        Course newCourse = new Course("TEST1000", "NewTestingCourse");
        handler.updateCourse(oldCourse, newCourse);

        Course actualCourse = handler.getCourse("TEST1000");
        assertEquals("TEST1000", actualCourse.getCourseId());
        assertEquals("NewTestingCourse", actualCourse.getCourseName());

        System.out.println("Finished testing updateCourse - Success");
    }

    @Test
    public void testDeleteCourse_Success() {
        System.out.println("Testing deleteCourse - Success");

        List<Course> courseList = handler.getCourses();
        Course newCourse = new Course("FAKE1000", "Fake Course");
        int expected = courseList.size();

        handler.addCourse(newCourse);
        handler.deleteCourse(newCourse);

        assertEquals(expected, courseList.size());

        System.out.println("Finished Testing deleteCourse - Success");
    }

    @Test
    public void testDeleteCourse_FailureCourseNotFound() {
        System.out.println("Testing deleteCourse - Failure Course Not Found");

        List<Course> courseList = handler.getCourses();
        Course newCourse = new Course("FAKE1000", "Fake Course");
        int expected = courseList.size();

        handler.deleteCourse(newCourse);

        assertEquals(expected, courseList.size());

        System.out.println("Finished Testing deleteCourses - Failure Course Not Found");
    }

    @After
    public void tearDown() {
        // reset DB
        this.tempDb.delete();
    }

    @AfterClass
    public static void finish() { System.out.println("Finished tests for CourseHandler"); }
}

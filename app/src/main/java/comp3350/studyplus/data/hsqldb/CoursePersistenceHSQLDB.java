package comp3350.studyplus.data.hsqldb;

import java.util.ArrayList;

import android.util.Log;

import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

import comp3350.studyplus.application.Services;
import comp3350.studyplus.data.IQuestionPersistence;
import comp3350.studyplus.objects.Course;
import comp3350.studyplus.data.ICoursePersistence;
import comp3350.studyplus.objects.QuestionTag;

public class CoursePersistenceHSQLDB implements ICoursePersistence {

    private List<Course> courseList;
    private final String dbPath;

    public CoursePersistenceHSQLDB(String dbPathName) {
        this.dbPath = dbPathName;
        this.courseList = new ArrayList<>();
        loadCourses();
    }

    protected Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }

    private Course fromResultSet(final ResultSet rs) throws SQLException {
        String courseId = rs.getString("COURSEID");
        String courseName = rs.getString("COURSENAME");

        return new Course(courseId, courseName);
    }

    private String fromResultSetQuestions(final ResultSet rs) throws SQLException {
        return rs.getString("QUESTIONID");
    }

    private void loadCourses() {
        try (final Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM COURSES"
            );

            while (resultSet.next()) {
                final Course course = fromResultSet(resultSet);
                this.courseList.add(course);
            }
            resultSet.close();
            statement.close();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public Course addCourse(Course currentCourse) {
        String courseId = currentCourse.getCourseId();
        String courseName = currentCourse.getCourseName();
        try (final Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO COURSES VALUES(?, ?)"
            );
            statement.setString(1, courseId);
            statement.setString(2, courseName);

            statement.executeUpdate();
            statement.close();

            // adds the course to the list
            this.courseList.add(currentCourse);
            return currentCourse;

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Course updateCourse(Course oldCourse, Course newCourse) {
        String oldCourseId = oldCourse.getCourseId();
        String newCourseId = newCourse.getCourseId();
        String newCourseName = newCourse.getCourseName();
        try (final Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement(
                    "UPDATE COURSES SET COURSEID = ?, COURSENAME = ? WHERE COURSEID = ?"
            );
            statement.setString(1, newCourseId);
            statement.setString(2, newCourseName);
            statement.setString(3, oldCourseId);

            statement.executeUpdate();
            statement.close();

            for (int i = 0; i < courseList.size(); i++) {
                if (courseList.get(i).getCourseId().equals(oldCourse.getCourseId())) {
                    courseList.set(i, newCourse);
                    return newCourse;
                }
            }

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteCourse(Course currentCourse) {
        String courseId = currentCourse.getCourseId();
        try (final Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM COURSES WHERE COURSES.COURSEID = ?"
            );
            statement.setString(1, courseId);

            statement.executeUpdate();
            statement.close();

            deleteAllQuestionsByTag(currentCourse.getQuestionTag());

            for (Course course : courseList) {
                if (course.getCourseId().equals(courseId)) {
                    courseList.remove(course);
                    break;
                }
            }

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    private void deleteAllQuestionsByTag(QuestionTag questionTag) {
        String questionTagId = questionTag.getTagId();
        String tagIdStr = ""+questionTagId;

        try (final Connection connection = connect()) {
            final PreparedStatement selectStatement = connection.prepareStatement(
                    "SELECT * FROM QUESTION_TAGS " +
                    "WHERE QUESTION_TAGS.TAGID = ?"
            );
            selectStatement.setString(1, tagIdStr);

            final ResultSet resultSet = selectStatement.executeQuery();

            IQuestionPersistence questionPersistence = Services.getQuestionPersistence();

            while (resultSet.next()) {
                final String questionId = fromResultSetQuestions(resultSet);
                // get the Question DB to delete the question so it updates its own list
                questionPersistence.deleteQuestionById(UUID.fromString(questionId));
            }
            resultSet.close();
            selectStatement.close();

            final PreparedStatement deleteStatement = connection.prepareStatement(
                    "DELETE FROM QUESTION_TAGS WHERE TAGID = ?"
            );
            deleteStatement.setString(1, questionTagId);

            deleteStatement.executeUpdate();
            deleteStatement.close();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getCoursesList() {
        return courseList;
    }
}

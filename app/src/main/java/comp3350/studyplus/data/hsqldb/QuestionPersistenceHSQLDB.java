package comp3350.studyplus.data.hsqldb;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import comp3350.studyplus.data.IQuestionPersistence;
import comp3350.studyplus.objects.Question;
import comp3350.studyplus.objects.QuestionTag;

public class QuestionPersistenceHSQLDB implements IQuestionPersistence {

    private List<Question> questionList;
    private final String dbPath;

    public QuestionPersistenceHSQLDB(String dbPathName) {
        this.dbPath = dbPathName;
        this.questionList = new ArrayList<>();
        loadQuestions();
    }

    protected Connection connect() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }


    private Question fromResultSet(final ResultSet rs) throws SQLException {
        String id = rs.getString("QUESTIONID");
        String question = rs.getString("QUESTION");
        String answer = rs.getString("ANSWER");

        return new Question(id, question, answer);
    }

    private void loadQuestions() {
        try (final Connection connection = connect()) {
            final Statement statement = connection.createStatement();
            final ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM QUESTIONS"
            );

            while (resultSet.next()) {
                final Question question = fromResultSet(resultSet);
                this.questionList.add(question);
            }
            resultSet.close();
            statement.close();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public List<Question> getQuestionList() {
        return questionList;
    }


    @Override
    public Question addQuestion(Question question) {
        try (final Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO QUESTIONS VALUES(?, ?, ?)"
            );
            statement.setString(1, question.getQuestionId().toString());
            statement.setString(2, question.getQuestion());
            statement.setString(3, question.getAnswer());

            statement.executeUpdate();
            statement.close();

            // adds the question to the list
            this.questionList.add(question);
            return question;

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteQuestionById(UUID questionId) {
        try (final Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM QUESTIONS WHERE QUESTIONS.QUESTIONID = ?"
            );
            statement.setString(1, questionId.toString());

            statement.executeUpdate();
            statement.close();

            deleteQuestionTag(questionId);

            for (Question question : questionList) {
                if (question.getQuestionId().equals(questionId)) {
                    questionList.remove(question);
                    break;
                }
            }

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }

    @Override
    public QuestionTag addQuestionTag(Question question, QuestionTag questionTag) {
        String questionTagId = questionTag.getTagId();
        String questionId = question.getQuestionId().toString();

        try (final Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO QUESTION_TAGS VALUES(?, ?)"
            );
            statement.setString(1, questionId);
            statement.setString(2, questionTagId);

            statement.executeUpdate();
            statement.close();

            return questionTag;

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Question> getQuestionsByTag(QuestionTag targetTag) {
        List<Question> questions = new ArrayList<>();
        String tagId = targetTag.getTagId();

        try (final Connection connection = connect()) {

            final PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM QUESTIONS " +
                            "JOIN QUESTION_TAGS ON QUESTIONS.QUESTIONID = QUESTION_TAGS.QUESTIONID " +
                            "WHERE TAGID = ?"
            );

            statement.setString(1, tagId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                final Question question = fromResultSet(resultSet);
                questions.add(question);
            }

            resultSet.close();
            statement.close();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }

        return questions;
    }

    @Override
    public Question updateQuestion(Question updatedQuestion) {
        Question question = null;
        String newQuestionId = updatedQuestion.getQuestionId().toString();
        String questionStr = updatedQuestion.getQuestion();
        String answerStr = updatedQuestion.getAnswer();

        try (final Connection connection = connect()) {

                final PreparedStatement statement = connection.prepareStatement(
                        "UPDATE QUESTIONS SET QUESTION = ?, ANSWER = ? WHERE QUESTIONID = ?"
                );
                statement.setString(1, questionStr);
                statement.setString(2, answerStr);
                statement.setString(3, newQuestionId);

                statement.executeUpdate();
                statement.close();

                for (int i = 0; i < questionList.size(); i++) {
                    if (questionList.get(i).getQuestionId().equals(newQuestionId)) {
                        questionList.set(i, updatedQuestion);
                        question = questionList.get(i);
                    }
                }

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
        return question;
    }

    private void deleteQuestionTag(UUID questionId) {
        try (final Connection connection = connect()) {
            final PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM QUESTION_TAGS WHERE QUESTION_TAGS.QUESTIONID = ?"
            );
            statement.setString(1, questionId.toString());

            statement.executeUpdate();
            statement.close();

        } catch (final SQLException e) {
            Log.e("Connect SQL", e.getMessage() + e.getSQLState());
            e.printStackTrace();
        }
    }
}

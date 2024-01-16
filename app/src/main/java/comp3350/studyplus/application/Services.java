package comp3350.studyplus.application;

import comp3350.studyplus.data.ICoursePersistence;
import comp3350.studyplus.data.IQuestionPersistence;
import comp3350.studyplus.data.hsqldb.QuestionPersistenceHSQLDB;
import comp3350.studyplus.data.hsqldb.CoursePersistenceHSQLDB;

public class Services {
    public static IQuestionPersistence questionPersistence = null;
    public static ICoursePersistence coursePersistence = null;

    public static synchronized IQuestionPersistence getQuestionPersistence() {
        if (questionPersistence == null) {
            questionPersistence = new QuestionPersistenceHSQLDB(Main.getDBPathName());
        }
        return questionPersistence;
    }

    public static synchronized ICoursePersistence getCoursePersistence() {
        if (coursePersistence == null) {
            coursePersistence = new CoursePersistenceHSQLDB(Main.getDBPathName());
        }
        return coursePersistence;
    }
}
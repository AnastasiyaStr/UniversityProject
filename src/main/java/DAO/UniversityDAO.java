package DAO;

import entity.University;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

/**
 * class to access info about university in database
 */
public class UniversityDAO {
    private static final Logger logger = LogManager.getLogger("FileAppender");
    private static final String SUCCESS_MSG = "\nSuccessfully Created Records In The Database!\n";
    private static final String ROLLBACK_MSG = "\nTransaction Is Being Rolled Back\n";
    private static final String COULD_NOT_PERF_MSG = "Could not perform operation - we'll figure out what happened";
    private static Session SESSION;

    public static void createRecord() {
        University university;
        try {
            SESSION = HibernateUtil.getSessionFactory().openSession();
            SESSION.beginTransaction();
            university = new University();
            university.setUniversityName("Hogwarts");
            SESSION.save(university);
            SESSION.getTransaction().commit();
           logger.info(SUCCESS_MSG);
        } catch (Exception sqlException) {
            if (null != SESSION.getTransaction()) {
               logger.warn(ROLLBACK_MSG);
                SESSION.getTransaction().rollback();
            }
            System.out.println(COULD_NOT_PERF_MSG);

        } finally {
            if (SESSION != null) {
                SESSION.close();
            }
        }
    }


}

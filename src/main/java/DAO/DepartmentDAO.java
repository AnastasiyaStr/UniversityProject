package DAO;

import entity.Degree;
import entity.Department;
import entity.Lector;
import entity.University;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


/**
 *The class contains methods to return data about department
 */
public class DepartmentDAO {
    private static final Logger logger = LogManager.getLogger("FileAppender");
    private static final String SUCCESS_MSG = "\nSuccessfully Created Records In The Database!\n";
    private static final String ROLLBACK_MSG = "\nTransaction Is Being Rolled Back\n";
    private static final String COULD_NOT_PERF_MSG = "Could not perform operation - we'll figure out what happened";
    private static final Integer DEPARTMENT_COUNT = 5;
    private static Session SESSION;

    public static void createRecord() {
        Department department = null;
        try {
            SESSION = HibernateUtil.getSessionFactory().openSession();
            SESSION.beginTransaction();
            for (int j = 1; j <= DEPARTMENT_COUNT; j++) {
                department = new Department();
                department.setId(j);
                department.setDepartmentName("Dep" + j);
                department.setHeadOfDepartment(SESSION.get(Lector.class, j));
                department.setUniversity( SESSION.get(University.class, 1));
                SESSION.save(department);
            }
            SESSION.getTransaction().commit();
            logger.info(SUCCESS_MSG );
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


    public static void showCountOfEmployee(String departmentName) {
        try {
            SESSION = HibernateUtil.getSessionFactory().openSession();
            SESSION.beginTransaction();
        CriteriaBuilder builder = SESSION.getCriteriaBuilder();
        CriteriaQuery<Department> query = builder.createQuery(Department.class);
        Root<Department> root = query.from(Department.class);
        query.select(root).where(builder.equal(root.get("departmentName"), departmentName));
        Query<Department> q = SESSION.createQuery(query);
        Department department;
        try {
            department = q.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No information found!");
            return;
        }
        System.out.println("Count of employee: " + department.getLectors().size());

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

    public static void showAverageSalaryForDepartment(String departmentName) {
        try {
            SESSION = HibernateUtil.getSessionFactory().openSession();
            SESSION.beginTransaction();
        CriteriaBuilder builder = SESSION.getCriteriaBuilder();
        CriteriaQuery<Department> query = builder.createQuery(Department.class);
        Root<Department> root = query.from(Department.class);
        query.select(root).where(builder.equal(root.get("departmentName"), departmentName));
        Query<Department> q = SESSION.createQuery(query);
        Department department = null;
        try {
            department = q.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No information found!");
            return;
        }
        double average = department.getLectors().stream()
                .mapToDouble(Lector::getSalary)
                .average()
                .orElse(0);
        System.out.println("The average salary of " + departmentName + " is " + average);

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


    public static void getLectorsByRank(String departmentname) {
        try {
            SESSION = HibernateUtil.getSessionFactory().openSession();
            SESSION.beginTransaction();
        SESSION = HibernateUtil.getSessionFactory().openSession();
        SESSION.beginTransaction();
        Object countAssistant = SESSION.createNamedQuery("get_by_rank").setParameter("degree", Degree.ASSISTANT).setParameter("dep_name", departmentname).getSingleResult();
        Object countProfessor = SESSION.createNamedQuery("get_by_rank").setParameter("degree", Degree.PROFESSOR).setParameter("dep_name", departmentname).getSingleResult();
        Object countAssosiateProfessor = SESSION.createNamedQuery("get_by_rank").setParameter("degree", Degree.ASSOSIATE_PROFESSOR).setParameter("dep_name", departmentname).getSingleResult();
        System.out.println("Assistants - " + countAssistant);
        System.out.println("Professors - " + countProfessor);
        System.out.println("Assosiate professors -  " + countAssosiateProfessor);

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

    public static void showHeadOfDepartment(String departmentName) {
        try {
            SESSION = HibernateUtil.getSessionFactory().openSession();
            SESSION.beginTransaction();
        CriteriaBuilder builder = SESSION.getCriteriaBuilder();
        CriteriaQuery<Department> query = builder.createQuery(Department.class);
        Root<Department> root = query.from(Department.class);
        query.select(root).where(builder.equal(root.get("departmentName"), departmentName));
        Query<Department> q = SESSION.createQuery(query);
        Department department = null;
        try {
            department = q.getSingleResult();
            System.out.println("Head of department " + departmentName + " is " + department.getHeadOfDepartment());
        } catch (NoResultException e) {
            System.out.println("No information found!");
        }
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


    public static String options() {
        return "\n1-Who is head of department - \n{ENTER DEPARTMENT}" +
                "\n2-Show statistic -  \n{ENTER DEPARTMENT}" +
                "\n3-Show average salary  -  \n{ENTER DEPARTMENT}" +
                "\n4-Show count of employee   -  \n{ENTER DEPARTMENT}";
    }
}



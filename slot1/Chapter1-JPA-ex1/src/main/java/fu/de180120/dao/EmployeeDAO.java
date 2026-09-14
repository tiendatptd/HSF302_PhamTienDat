package fu.de180120.dao;

import fu.de180120.pojo.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeDAO {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hsf302FU");

    public void save(Employee e) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            em.persist(e);

            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public Employee findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close(); // Dam bao luon dong EntityManager
        }
    }

    public List<Employee> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Employee findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Employee> result = em.createQuery(
                            "SELECT e FROM Employee e WHERE e.email = :email", Employee.class)
                    .setParameter("email", email)
                    .getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public List<Employee> findBySalaryGreaterThanAndActive(BigDecimal minSalary) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT e FROM Employee e WHERE e.salary > :minSalary AND e.active = true",
                            Employee.class)
                    .setParameter("minSalary", minSalary)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Employee update(Employee e) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // Dung em.merge(e) de re-attach entity va cap nhat vao DB
            Employee mergedEntity = em.merge(e);

            em.getTransaction().commit();
            return mergedEntity;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public boolean delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            // 1. Find entity trong cùng EntityManager để đảm bảo entity ở trạng thái Managed
            Employee employee = em.find(Employee.class, id);

            // 2. Kiểm tra != null trước khi thực hiện remove
            if (employee != null) {
                em.remove(employee);
                em.getTransaction().commit();
                return true;
            }

            em.getTransaction().commit();
            return false;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
}
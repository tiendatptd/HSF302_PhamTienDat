package fu.de180120.dao;

import fu.de180120.pojo.Employee;
import fu.de180120.pojo.Project;
import fu.de180120.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class EmployeeDAO {

    public void save(Employee employee) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(employee);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public Employee findById(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            return em.createQuery(
                    "SELECT e FROM Employee e",
                    Employee.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public Employee update(Employee employee) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        Employee updatedEmployee = null;

        try {
            tx.begin();
            updatedEmployee = em.merge(employee);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        return updatedEmployee;
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Employee employee = em.find(Employee.class, id);

            if (employee != null) {
                em.remove(employee);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // TODO 5.6
    public void assignEmployeeToProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);

            if (employee == null) {
                throw new IllegalArgumentException(
                        "Employee not found: " + employeeId
                );
            }

            if (project == null) {
                throw new IllegalArgumentException(
                        "Project not found: " + projectId
                );
            }

            employee.assignToProject(project);

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }

            e.printStackTrace();

        } finally {
            em.close();
        }
    }
    public Employee findByIdWithProjects(Long id) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {
            return em.createQuery(
                            "SELECT DISTINCT e FROM Employee e " +
                                    "LEFT JOIN FETCH e.projects " +
                                    "WHERE e.id = :id",
                            Employee.class
                    )
                    .setParameter("id", id)
                    .getSingleResult();

        } catch (jakarta.persistence.NoResultException e) {
            return null;

        } finally {
            em.close();
        }
    }
    // TODO 5.9
    public void unassignEmployeeFromProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Employee employee = em.find(Employee.class, employeeId);
            Project project = em.find(Project.class, projectId);

            if (employee == null) {
                throw new IllegalArgumentException(
                        "Employee not found: " + employeeId
                );
            }

            if (project == null) {
                throw new IllegalArgumentException(
                        "Project not found: " + projectId
                );
            }

            employee.unassignFromProject(project);

            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }

            e.printStackTrace();

        } finally {
            em.close();
        }
    }

    // TODO 5.10
    public List<Employee> findActiveEmployeesWithMoreThanOneProject() {

        EntityManager em = JPAUtil.getEMF().createEntityManager();

        try {

            return em.createQuery(
                    "SELECT DISTINCT e " +
                            "FROM Employee e " +
                            "JOIN FETCH e.projects " +
                            "WHERE e.active = true " +
                            "AND e.id IN (" +
                            "    SELECT e2.id " +
                            "    FROM Employee e2 " +
                            "    JOIN e2.projects p2 " +
                            "    WHERE e2.active = true " +
                            "    GROUP BY e2.id " +
                            "    HAVING COUNT(p2) > 1" +
                            ")",
                    Employee.class
            ).getResultList();

        } finally {

            em.close();
        }
    }
}
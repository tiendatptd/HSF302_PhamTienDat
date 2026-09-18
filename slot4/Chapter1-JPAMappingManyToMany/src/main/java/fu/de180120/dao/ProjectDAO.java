package fu.de180120.dao;

import fu.de180120.pojo.Employee;
import fu.de180120.pojo.Project;
import fu.de180120.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ProjectDAO {

    public void save(Project project) {
        EntityManager em = JPAUtil.getEMF().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(project);
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
}
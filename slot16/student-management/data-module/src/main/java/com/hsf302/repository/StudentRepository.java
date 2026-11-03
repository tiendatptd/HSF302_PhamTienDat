package com.hsf302.repository;

import com.hsf302.entity.Student;
import org.hibernate.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.sql.Statement;
import java.util.List;

public class StudentRepository {
    private final EntityManagerFactory emf;

    public StudentRepository() {
        // Tên "student_pu" phải khớp với name trong thẻ <persistence-unit> ở file persistence.xml
        this.emf = Persistence.createEntityManagerFactory("student_pu");
        migrateNameColumnsToUnicode();
    }

    private void migrateNameColumnsToUnicode() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.unwrap(Session.class).doWork(connection -> {
                try (Statement statement = connection.createStatement()) {
                    statement.execute("""
                            IF EXISTS (
                                SELECT 1 FROM sys.columns
                                WHERE object_id = OBJECT_ID(N'students')
                                  AND name = N'firstName'
                                  AND TYPE_NAME(user_type_id) <> N'nvarchar'
                            )
                            ALTER TABLE students ALTER COLUMN firstName NVARCHAR(255) NOT NULL
                            """);
                    statement.execute("""
                            IF EXISTS (
                                SELECT 1 FROM sys.columns
                                WHERE object_id = OBJECT_ID(N'students')
                                  AND name = N'lastName'
                                  AND TYPE_NAME(user_type_id) <> N'nvarchar'
                            )
                            ALTER TABLE students ALTER COLUMN lastName NVARCHAR(255) NOT NULL
                            """);
                }
            });
            em.getTransaction().commit();
        }
    }

    public void save(Student student) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        }
    }

    public boolean existsByEmail(String email) {
        try (EntityManager em = emf.createEntityManager()) {
            Long count = em.createQuery(
                            "SELECT COUNT(s) FROM Student s WHERE LOWER(s.email) = LOWER(:email)",
                            Long.class)
                    .setParameter("email", email.trim())
                    .getSingleResult();
            return count > 0;
        }
    }

    public List<Student> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        }
    }

    public void update(Student student) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(student); // merge dùng để cập nhật entity đã tồn tại
            em.getTransaction().commit();
        }
    }

    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if (student != null) {
                em.remove(student);
            }
            em.getTransaction().commit();
        }
    }
    public List<Student> search(String keyword) {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT s FROM Student s WHERE LOWER(s.firstName) LIKE LOWER(:kw) " +
                    "OR LOWER(s.lastName) LIKE LOWER(:kw) " +
                    "OR LOWER(s.email) LIKE LOWER(:kw)";
            return em.createQuery(jpql, Student.class)
                    .setParameter("kw", "%" + keyword + "%")
                    .getResultList();
        }
    }
}
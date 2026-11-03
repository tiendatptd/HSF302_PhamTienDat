package com.hsf302.repository;

import com.hsf302.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class StudentRepository {
    private final EntityManagerFactory emf;

    public StudentRepository() {
        // Tên "student_pu" phải khớp với name trong thẻ <persistence-unit> ở file persistence.xml
        this.emf = Persistence.createEntityManagerFactory("student_pu");
    }

    public void save(Student student) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
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
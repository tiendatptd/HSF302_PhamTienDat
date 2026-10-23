package com.hsf302.student.repository;

import com.hsf302.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Spring Data JPA đã tự động cung cấp các hàm save(), findAll(), findById(), deleteById()...
    List<Student> findByNameContainingIgnoreCase(String keyword);
}
package com.hsf302.student.repository;

import com.hsf302.student.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContainingIgnoreCase(String keyword);
    Page<Student> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
}
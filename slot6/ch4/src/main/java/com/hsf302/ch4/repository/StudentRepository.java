package com.hsf302.ch4.repository;

import com.hsf302.ch4.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>,
        JpaSpecificationExecutor<Student> {
    // ===== Part C — Derived query =====
    Optional<Student> findByStudentCode(String studentCode);   // Tự sinh: WHERE student_code = ?
    boolean existsByEmail(String email);                       // Kiểm tra tồn tại
    long countByActiveTrue();                                  // Tự sinh: WHERE active = 1 (không cần tham số)
}

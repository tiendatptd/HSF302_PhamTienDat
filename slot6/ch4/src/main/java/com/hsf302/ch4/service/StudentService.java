package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    long count();
    Optional<Student> findById(Long id);
    List<Student> findAllOrderByGpaDesc();
    Page<Student> findPage(int pageIndex, int size, String sortField);
    // ===== Part C — Derived query =====
    Optional<Student> findByStudentCode(String studentCode);
    boolean isEmailExisted(String email);
    long countActive();
    List<Student> searchByName(String keyword);
    List<Student> findByEmailDomain(String domain);
    List<Student> findWithoutEmail();
    List<Student> findByGpaRange(double min, double max);   // TODO 10a
    List<Student> findActiveByGender(Gender gender);        // TODO 10b
    List<Student> findBornAfter(LocalDate date);            // TODO 10c
    List<Student> findByDepartment(String deptCode);    // TODO 11a
    long countByDepartment(String deptCode);            // TODO 11b (dùng lại ở TODO 22)
    List<Student> findTop3ByGpa();                      // TODO 11c
}
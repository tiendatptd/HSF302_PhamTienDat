package com.hsf302.student.service;

import com.hsf302.student.entity.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student findById(Long id);
    void save(Student student);
    void deleteById(Long id);
    List<Student> searchByName(String keyword);
    Page<Student> findAll(int pageNo, int pageSize);
    Page<Student> searchByName(String keyword, int pageNo, int pageSize);
    Page<Student> findAll(int pageNo, int pageSize, String sortField, String sortDir);
    Page<Student> searchByName(String keyword, int pageNo, int pageSize, String sortField, String sortDir);
}
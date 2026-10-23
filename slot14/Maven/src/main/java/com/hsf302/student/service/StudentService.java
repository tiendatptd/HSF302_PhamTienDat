package com.hsf302.student.service;

import com.hsf302.student.entity.Student;
import java.util.List;

public interface StudentService {
    List<Student> findAll();
    Student findById(Long id);
    void save(Student student);
    void deleteById(Long id);
    List<Student> searchByName(String keyword);
}
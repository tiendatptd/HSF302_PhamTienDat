package com.hsf302.service;

import com.hsf302.entity.Student;
import com.hsf302.repository.StudentRepository;
import java.util.List;

public class StudentService {

    private final StudentRepository repository;

    public StudentService() {
        this.repository = new StudentRepository();
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public boolean saveStudent(Student student) {
        if (repository.existsByEmail(student.getEmail())) {
            return false;
        }
        repository.save(student);
        return true;
    }

    public void updateStudent(Student student) {
        repository.update(student);
    }

    public void deleteStudent(Long id) {
        repository.delete(id);
    }

    public List<Student> searchStudents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllStudents(); // Nếu không nhập gì thì trả về tất cả
        }
        return repository.search(keyword.trim());
    }
}

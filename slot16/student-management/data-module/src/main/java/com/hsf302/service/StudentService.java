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

    public void saveStudent(Student student) {
        // Bạn có thể thêm các logic kiểm tra (validate) nghiệp vụ ở đây trước khi lưu
        repository.save(student);
    }

    public void updateStudent(Student student) {
        repository.update(student);
    }

    public void deleteStudent(Long id) {
        repository.delete(id);
    }
}


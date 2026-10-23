package com.hsf302.student.service;

import com.hsf302.student.entity.Student;
import com.hsf302.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> findAll() { return studentRepository.findAll(); }

    @Override
    public Student findById(Long id) { return studentRepository.findById(id).orElse(null); }

    @Override
    public void save(Student student) { studentRepository.save(student); }

    @Override
    public void deleteById(Long id) { studentRepository.deleteById(id); }

    @Override
    public List<Student> searchByName(String keyword) {
        return studentRepository.findByNameContainingIgnoreCase(keyword);
    }
}
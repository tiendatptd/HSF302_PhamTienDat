package com.hsf302.student.service;

import com.hsf302.student.entity.Student;
import com.hsf302.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Student> findAll(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> searchByName(String keyword, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return studentRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }
}
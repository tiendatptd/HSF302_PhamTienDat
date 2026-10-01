package com.hsf302.ch4.service;

import com.hsf302.ch4.pojo.Course;
import com.hsf302.ch4.pojo.Student;

import java.util.List;

public interface EnrollmentService {
    // ===== Part B =====
    List<Course> getCoursesOfStudent(String studentCode);
    List<Student> getStudentsOfCourse(String courseCode);
    // ===== Part C =====
    List<Student> findStudentsInCourse(String courseCode);
    long countStudentsInCourse(String courseCode);
    List<Student> findActiveStudentsInCourse(String courseCode);
    List<Student> findStudentsWithoutCourses();
    boolean isEnrolled(String studentCode, String courseCode);
}
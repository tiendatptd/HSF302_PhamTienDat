package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.CourseEnrollmentCount;
import com.hsf302.ch4.dto.CourseStatDTO;
import com.hsf302.ch4.pojo.Course;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public long count() {
        return courseRepository.count();
    }

    @Override
    public List<Course> findAllOrderByCode() {
        return courseRepository.findAll(Sort.by("code"));
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }
    @Override
    public Optional<Course> findByCode(String code) {
        return courseRepository.findByCode(code);
    }

    @Override
    public List<Course> findBySemester(String semester) {
        return courseRepository.findBySemesterOrderByCodeAsc(semester);
    }

    @Override
    public long countBySemester(String semester) {
        return courseRepository.countBySemester(semester);
    }

    @Override
    public List<Course> findCoursesOfStudent(String studentCode) {
        return courseRepository.findByStudents_StudentCodeOrderByCodeAsc(studentCode);
    }

    @Override
    public List<Course> findCoursesOfDepartment(String deptCode, boolean distinct) {
        return distinct
                ? courseRepository.findDistinctByStudents_Department_CodeOrderByCodeAsc(deptCode)
                : courseRepository.findByStudents_Department_CodeOrderByCodeAsc(deptCode);
    }

    @Override
    public List<Course> findCoursesWithoutStudents() {
        return courseRepository.findByStudentsIsEmpty();
    }

    @Override
    public List<CourseStatDTO> getStatistics() {
        return courseRepository.getCourseStats();
    }

    @Override
    public List<Course> findFullCourses() {
        return courseRepository.findFullCourses();
    }

    @Override
    public Course getWithStudents(String code) {
        return courseRepository.findWithStudentsByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + code));
    }

    @Override
    public List<CourseEnrollmentCount> findTopEnrolled(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be > 0");
        }
        return courseRepository.findTopEnrolledNative(n);
    }

    @Override
    public Page<Course> searchCourses(String keyword, int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Invalid page or size");
        }
        // PageRequest đếm từ 0
        Pageable pageable = PageRequest.of(page, size, Sort.by("code").ascending());
        return courseRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }


    @Override
    public List<Course> searchByExample(Course probe) {
        // Cấu hình Matcher:
        // - Bỏ qua các field null
        // - Với kiểu String: tìm kiếm chứa chuỗi (contains) và không phân biệt hoa thường (ignore case)
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        // Tạo Example từ đối tượng mẫu và cấu hình Matcher
        Example<Course> example = Example.of(probe, matcher);

        // findAll(Example) đã được JpaRepository hỗ trợ sẵn
        return courseRepository.findAll(example);
    }

    // ===== Part E =====
    @Override
    @Transactional // Ghi đè cấu hình readOnly = true của class
    public int increaseCapacity(String semester, int bonus) {
        if (bonus <= 0) {
            throw new IllegalArgumentException("Bonus capacity must be > 0");
        }
        return courseRepository.increaseCapacityBySemester(semester, bonus);
    }

    @Override
    @Transactional // Bắt buộc để ghi dữ liệu
    public void deleteCourse(String code) {
        Course course = courseRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Cannot delete. Course not found: " + code));

        // Phải dọn dẹp Owning Side (Student) trước khi xóa Inverse Side (Course)
        // Tạo một list copy để tránh lỗi ConcurrentModificationException
        List<Student> enrolledStudents = new ArrayList<>(course.getStudents());
        for (Student s : enrolledStudents) {
            s.unenroll(course); // Xóa liên kết trong Java object -> Hibernate tự xóa ở bảng student_courses
        }

        // Sau khi không còn liên kết khóa ngoại, tiến hành xóa khóa học
        courseRepository.delete(course);
    }
}
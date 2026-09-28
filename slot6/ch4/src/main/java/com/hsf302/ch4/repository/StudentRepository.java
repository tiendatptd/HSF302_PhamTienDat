package com.hsf302.ch4.repository;

import com.hsf302.ch4.dto.StudentSummary;
import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long>,
        JpaSpecificationExecutor<Student> {
    // ===== Part C — Derived query =====
    Optional<Student> findByStudentCode(String studentCode);   // Tự sinh: WHERE student_code = ?
    boolean existsByEmail(String email);                       // Kiểm tra tồn tại
    long countByActiveTrue();                                  // Tự sinh: WHERE active = 1 (không cần tham số)
    List<Student> findByFullNameContainingIgnoreCase(String keyword);
    List<Student> findByEmailEndingWith(String suffix);
    List<Student> findByEmailIsNull();
    List<Student> findByGpaBetweenOrderByGpaDesc(double min, double max);   // gpa BETWEEN ? AND ? ORDER BY gpa DESC
    List<Student> findByGenderAndActiveTrue(Gender gender);                  // gender = ? AND active = 1
    List<Student> findByDobAfter(LocalDate date);                            // dob > ?
    List<Student> findByDepartment_CodeOrderByFullNameAsc(String code);   // Truy vấn qua thuộc tính lồng nhau (Nested property)
    long countByDepartment_Code(String code);
    List<Student> findTop3ByOrderByGpaDesc();                              // Lấy Top 3 GPA cao nhất
    @Query("SELECT s FROM Student s " +
            "WHERE s.department.code = :code AND s.gpa >= :minGpa " +
            "ORDER BY s.gpa DESC")
    List<Student> findGoodStudentsInDepartment(@Param("code") String code,
                                               @Param("minGpa") double minGpa);

    @Query("SELECT s FROM Student s " +
            "WHERE LOWER(s.fullName) LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "   OR LOWER(s.email)    LIKE LOWER(CONCAT('%', :kw, '%')) " +
            "ORDER BY s.fullName")
    List<Student> searchByKeyword(@Param("kw") String keyword);

    @Query("SELECT s FROM Student s " +
            "WHERE s.gpa > (SELECT AVG(s2.gpa) FROM Student s2) ORDER BY s.gpa DESC")
    List<Student> findAboveAverageGpa();

    @Query(value = "SELECT TOP (:n) s.* " +
            "FROM students s JOIN departments d ON s.department_id = d.id " +
            "WHERE d.code = :code " +
            "ORDER BY s.gpa DESC",
            nativeQuery = true)
    List<Student> findTopNByDepartmentNative(@Param("code") String code, @Param("n") int n);

    @Query("SELECT s.studentCode AS studentCode, s.fullName AS fullName, " +    // TODO 18
            "       s.gpa AS gpa, d.name AS departmentName " +
            "FROM Student s JOIN s.department d WHERE s.active = true ORDER BY s.fullName")
    List<StudentSummary> findActiveSummaries();
}

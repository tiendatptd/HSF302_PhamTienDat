package com.hsf302.ch4.repository;

import com.hsf302.ch4.dto.DepartmentStatDTO;
import com.hsf302.ch4.pojo.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // sẽ bổ sung sau
    List<Department> findByStudentsIsEmpty();            // Tìm các khoa chưa có sinh viên (IsEmpty)
    @Query("SELECT new com.hsf302.ch4.dto.DepartmentStatDTO(d.code, d.name, COUNT(s), AVG(s.gpa)) " +
            "FROM Department d LEFT JOIN d.students s " +
            "GROUP BY d.code, d.name ORDER BY d.code")
    List<DepartmentStatDTO> getDepartmentStats();                               // TODO 14, 23

    Optional<Department> findByCode(String code);                               // TODO 11, 16, 22

    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.students WHERE d.code = :code")
    Optional<Department> findByCodeWithStudents(@Param("code") String code);    // TODO 16
}
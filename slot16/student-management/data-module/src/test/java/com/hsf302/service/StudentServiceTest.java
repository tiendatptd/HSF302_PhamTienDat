package com.hsf302.service;

import com.hsf302.entity.Student;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {

    @Test
    public void testSaveAndGetAllStudents() {
        // 1. Khởi tạo service
        StudentService service = new StudentService();

        // 2. Tạo một sinh viên giả lập
        Student student = Student.builder()
                .firstName("Nguyen")
                .lastName("Van A")
                .email("nva@test.com")
                .build();

        // 3. Thực thi hành động lưu
        service.saveStudent(student);

        // 4. Lấy danh sách lên và kiểm tra (Assert) xem dữ liệu có đúng không
        List<Student> students = service.getAllStudents();

        assertFalse(students.isEmpty(), "Danh sách không được rỗng sau khi thêm");
        assertEquals("Nguyen", students.get(students.size() - 1).getFirstName(), "Tên sinh viên phải khớp");
        assertEquals("nva@test.com", students.get(students.size() - 1).getEmail(), "Email phải khớp");
    }
}

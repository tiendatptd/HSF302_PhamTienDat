package com.hsf302.student.config;

import com.hsf302.student.entity.Student;
import com.hsf302.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem trong DB đã có dữ liệu chưa, nếu chưa (count == 0) thì mới thêm
        if (studentRepository.count() == 0) {
            Student s1 = new Student();
            s1.setName("Nguyễn Văn A");
            s1.setEmail("nguyenvana@gmail.com");
            s1.setBirthDate(LocalDate.of(2003, 1, 15));
            s1.setGpa(8.5);

            Student s2 = new Student();
            s2.setName("Trần Thị B");
            s2.setEmail("tranthib@gmail.com");
            s2.setBirthDate(LocalDate.of(2004, 5, 20));
            s2.setGpa(9.2);

            Student s3 = new Student();
            s3.setName("Lê Văn C");
            s3.setEmail("levanc@gmail.com");
            s3.setBirthDate(LocalDate.of(2003, 10, 10));
            s3.setGpa(7.0);

            studentRepository.save(s1);
            studentRepository.save(s2);
            studentRepository.save(s3);

            System.out.println("====> Đã thêm dữ liệu mẫu (Seed Data) thành công!");
        }
    }
}
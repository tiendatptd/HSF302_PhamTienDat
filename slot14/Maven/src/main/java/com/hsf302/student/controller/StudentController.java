package com.hsf302.student.controller;

import com.hsf302.student.entity.Student;
import com.hsf302.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("student", new Student());
        return "student/form";
    }

    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student, BindingResult result) {
        // Kiểm tra lỗi Validation
        if (result.hasErrors()) {
            return "student/form";
        }
        studentService.save(student);
        return "redirect:/students"; // Quay về trang danh sách
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Student student = studentService.findById(id);
        if (student != null) {
            model.addAttribute("student", student);
            return "student/form";
        }
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteById(id);
        return "redirect:/students";
    }

    @GetMapping
    public String listStudents(Model model,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {
        int pageSize = 5; // Số lượng sinh viên trên 1 trang
        Page<Student> page;

        if (keyword != null && !keyword.trim().isEmpty()) {
            page = studentService.searchByName(keyword, pageNo, pageSize);
            model.addAttribute("keyword", keyword);
        } else {
            page = studentService.findAll(pageNo, pageSize);
            model.addAttribute("keyword", "");
        }

        model.addAttribute("students", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        return "student/list";
    }
}
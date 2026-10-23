package com.hsf302.student.controller;

import com.hsf302.student.entity.Student;
import com.hsf302.student.service.StudentService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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


    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Student student = studentService.findById(id);
        if (student != null) {
            model.addAttribute("student", student);
            return "student/form";
        }
        return "redirect:/students";
    }


    @GetMapping
    public String listStudents(Model model,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               @RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
                               @RequestParam(value = "sortField", defaultValue = "name") String sortField,
                               @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir) {
        int pageSize = 5;
        Page<Student> page;

        if (keyword != null && !keyword.trim().isEmpty()) {
            page = studentService.searchByName(keyword, pageNo, pageSize, sortField, sortDir);
            model.addAttribute("keyword", keyword);
        } else {
            page = studentService.findAll(pageNo, pageSize, sortField, sortDir);
            model.addAttribute("keyword", "");
        }

        model.addAttribute("students", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "student/list";
    }

    @PostMapping("/save")
    public String saveStudent(@Valid @ModelAttribute("student") Student student,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "student/form";
        }
        boolean isNew = (student.getId() == null);
        studentService.save(student);

        if (isNew) {
            redirectAttributes.addFlashAttribute("successMessage", "Thêm sinh viên mới thành công!");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thông tin sinh viên thành công!");
        }
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        studentService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa sinh viên thành công!");
        return "redirect:/students";
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        // Thiết lập định dạng trả về là CSV
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"students.csv\"");

        PrintWriter writer = response.getWriter();
        // Thêm ký tự BOM (Byte Order Mark) để Excel nhận diện đúng tiếng Việt UTF-8
        writer.write('\ufeff');

        // Ghi dòng tiêu đề (Header)
        writer.println("ID,Tên Sinh Viên,Email,Ngày Sinh,GPA");

        // Lấy toàn bộ dữ liệu và ghi từng dòng
        List<Student> students = studentService.getAllStudents();
        for (Student student : students) {
            writer.printf("%d,%s,%s,%s,%.1f\n",
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getBirthDate(),
                    student.getGpa());
        }
    }
}
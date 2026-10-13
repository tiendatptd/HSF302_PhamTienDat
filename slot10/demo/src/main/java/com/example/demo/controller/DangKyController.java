package com.example.demo.controller;

import com.example.demo.model.DangKyForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dangky")
public class DangKyController {

    // GET – Hiển thị form đăng ký rỗng
    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("dangKyForm", new DangKyForm());
        return "dangky/form";
    }

    // POST – Kiểm tra dữ liệu (@Valid) và xử lý kết quả
    @PostMapping
    public String xuLyDangKy(@Valid @ModelAttribute("dangKyForm") DangKyForm form,
                             BindingResult result,
                             RedirectAttributes ra) {

        // 1. Nếu có lỗi validation -> trả thẳng về lại view form (KHÔNG redirect)
        if (result.hasErrors()) {
            return "dangky/form";
        }

        // 2. Nếu dữ liệu hợp lệ -> xử lý lưu/đăng ký rồi REDIRECT (chuẩn PRG)
        ra.addFlashAttribute("successMsg",
                "Đăng ký thành công tài khoản: " + form.getHoTen() + " (" + form.getEmail() + ")");
        return "redirect:/dangky";
    }
}
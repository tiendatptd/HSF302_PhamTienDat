package com.example.demo.model;

import jakarta.validation.constraints.*;

public class DangKyForm {

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 50, message = "Họ tên phải từ 2 đến 50 ký tự")
    private String hoTen;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng (ví dụ: ten@domain.com)")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 30, message = "Mật khẩu phải từ 6 đến 30 ký tự")
    private String matKhau;

    @NotNull(message = "Vui lòng nhập tuổi")
    @Min(value = 18, message = "Bạn phải từ 18 tuổi trở lên")
    @Max(value = 100, message = "Tuổi không được vượt quá 100")
    private Integer tuoi; // Dùng Integer (Wrapper) để khi bỏ trống sẽ rơi vào @NotNull thay vì lỗi 400

    public DangKyForm() {}

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }

    public Integer getTuoi() { return tuoi; }
    public void setTuoi(Integer tuoi) { this.tuoi = tuoi; }
}
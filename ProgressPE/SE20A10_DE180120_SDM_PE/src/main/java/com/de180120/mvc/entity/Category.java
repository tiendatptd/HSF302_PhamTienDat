package com.de180120.mvc.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories") // Sửa lại nếu tên bảng trong DB khác
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Sửa lại nếu tên cột ID khác (ví dụ: category_id)
    private Integer id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    // Quan hệ 1-N với bảng thiết bị (Device/SmartDevice)
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Device> devices; // Đổi tên class Device cho khớp với entity bên dưới

    // Constructors
    public Category() {
    }

    public Category(Integer id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    // Generate Getters và Setters (Alt + Insert -> Getter and Setter)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}

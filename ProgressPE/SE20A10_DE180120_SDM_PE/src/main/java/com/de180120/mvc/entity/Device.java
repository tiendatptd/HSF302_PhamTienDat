package com.de180120.mvc.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "devices") // Thay bằng tên bảng thực tế (ví dụ: smart_devices)
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "status")
    private String status;

    // Các trường khác tuỳ theo DB (ví dụ: price, description, manufacture_date...)
    // @Column(name = "...")
    // private Double price;

    // Quan hệ N-1 với bảng Category
    @ManyToOne
    @JoinColumn(name = "category_id") // Tên cột khóa ngoại trong DB
    private Category category;

    // Constructors
    public Device() {
    }

    // Generate Getters và Setters cho toàn bộ thuộc tính
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
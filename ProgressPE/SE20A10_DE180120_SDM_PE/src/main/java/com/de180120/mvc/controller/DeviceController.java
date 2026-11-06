package com.de180120.mvc.controller;

import com.de180120.mvc.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    // Hiển thị danh sách thiết bị tại trang chủ "/" hoặc "/devices"
    @GetMapping({"/", "/devices"})
    public String listDevices(Model model) {
        model.addAttribute("devices", deviceService.findAll());
        return "index";
    }
}
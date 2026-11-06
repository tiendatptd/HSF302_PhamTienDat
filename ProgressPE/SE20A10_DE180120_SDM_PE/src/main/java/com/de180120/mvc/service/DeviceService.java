package com.de180120.mvc.service;

import com.de180120.mvc.entity.Device;
import java.util.List;

public interface DeviceService {
    List<Device> findAll();
    Device findById(Integer id);
    void save(Device device);
    void deleteById(Integer id);
}
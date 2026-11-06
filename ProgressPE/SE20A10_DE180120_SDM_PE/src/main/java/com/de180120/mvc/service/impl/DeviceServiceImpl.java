package com.de180120.mvc.service.impl;

import com.de180120.mvc.entity.Device;
import com.de180120.mvc.repository.DeviceRepository;
import com.de180120.mvc.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Device findById(Integer id) {
        return deviceRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Device device) {
        deviceRepository.save(device);
    }

    @Override
    public void deleteById(Integer id) {
        deviceRepository.deleteById(id);
    }
}
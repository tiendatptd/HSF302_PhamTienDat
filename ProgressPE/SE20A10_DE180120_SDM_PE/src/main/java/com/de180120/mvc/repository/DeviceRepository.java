package com.de180120.mvc.repository;

import com.de180120.mvc.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Integer> {
    List<Device> findAllByOrderByInstallationDateDesc();
    List<Device> findByDeviceNameContainingIgnoreCaseOrderByInstallationDateDesc(String deviceName);
    Optional<Device> findByDeviceCode(String deviceCode);
}

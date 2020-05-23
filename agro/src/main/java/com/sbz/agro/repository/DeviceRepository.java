package com.sbz.agro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbz.agro.enums.DeviceDetails;
import com.sbz.agro.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByArray_Id(Long arrayId);

    Device findByIdAndArray_Id(Long id, Long arrayId);

    Device findByArray_IdAndPositionAndType(Long arrayId, Integer position, DeviceDetails type);

    Device findBySerialNo(String serialNo);
}

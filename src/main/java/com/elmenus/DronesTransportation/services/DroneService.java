package com.elmenus.DronesTransportation.services;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;

import java.util.List;

public interface DroneService {
    List<DroneDto> getAllDrones();
    DroneDto getDroneById(String serialNumber);
    boolean isDroneExists(DroneDto droneDto);
    DroneDto save(DroneDto droneDto);
    void deleteById(String serialNumber);
    List<DroneDto> getAvailableDrones();
}

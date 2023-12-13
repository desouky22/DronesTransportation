package com.elmenus.DronesTransportation.services;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;

import java.util.List;
import java.util.Optional;

public interface DroneService {
    List<DroneDto> getAllDrones();
    DroneDto getDroneById(String serialNumber);
    boolean isDroneExists(DroneDto droneDto);
    DroneDto save(DroneDto droneDto);
    void deleteById(String serialNumber);
}

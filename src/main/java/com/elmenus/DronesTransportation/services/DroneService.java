package com.elmenus.DronesTransportation.services;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;

import java.util.List;
import java.util.Optional;

public interface DroneService {
    List<DroneDto> getAllDrones();
    Optional<DroneDto> getDroneById(String serialNumber);
    boolean isDroneExists(DroneDto droneDto);
    DroneDto save(DroneDto droneDto);
    boolean deleteById(String serialNumber);
}

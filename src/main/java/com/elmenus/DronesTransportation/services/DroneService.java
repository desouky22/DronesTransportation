package com.elmenus.DronesTransportation.services;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;

import java.util.List;

public interface DroneService {
    List<DroneDto> getAllDrones();
}

package com.elmenus.DronesTransportation.services.impl;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;
import com.elmenus.DronesTransportation.mappers.DroneMapper;
import com.elmenus.DronesTransportation.repositories.DroneRepository;
import com.elmenus.DronesTransportation.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;



@Service
public class DroneServiceImpl implements DroneService {
    private DroneRepository droneRepository;
    private DroneMapper droneMapper;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository, DroneMapper droneMapper){
        this.droneRepository = droneRepository;
        this.droneMapper = droneMapper;
    }

    @Override
    public List<DroneDto> getAllDrones() {
        List<Drone> drones = droneRepository.findAll();
        return drones.stream().map(drone -> droneMapper.mapToDto(drone)).collect(Collectors.toList());
    }
}

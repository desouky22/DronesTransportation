package com.elmenus.DronesTransportation.services.impl;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;
import com.elmenus.DronesTransportation.errors.RecordNotFoundException;
import com.elmenus.DronesTransportation.mappers.DroneMapper;
import com.elmenus.DronesTransportation.repositories.DroneRepository;
import com.elmenus.DronesTransportation.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    @Override
    public DroneDto getDroneById(String serialNumber) {
        Optional<Drone> result = droneRepository.findById(serialNumber);
        if(result.isPresent())
            return droneMapper.mapToDto(result.get());
        throw new RecordNotFoundException("There is no Drone with ID = " + serialNumber);
    }

    @Override
    public boolean isDroneExists(DroneDto droneDto) {
        return droneRepository.existsById(droneDto.getSerialNumber());
    }

    @Override
    public DroneDto save(DroneDto droneDto) {
        Drone drone = droneMapper.mapToEntity(droneDto);
        Drone savedDrone = droneRepository.save(drone);
        return droneMapper.mapToDto(savedDrone);
    }

    @Override
    public boolean deleteById(String serialNumber) {
        if(droneRepository.existsById(serialNumber)) {
            droneRepository.deleteById(serialNumber);
            return true;
        }
        return false;
    }
}

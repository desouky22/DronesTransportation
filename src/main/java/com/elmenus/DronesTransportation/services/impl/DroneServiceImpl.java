package com.elmenus.DronesTransportation.services.impl;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;
import com.elmenus.DronesTransportation.errors.RecordNotFoundException;
import com.elmenus.DronesTransportation.mappers.DroneMapper;
import com.elmenus.DronesTransportation.mappers.MedicationMapper;
import com.elmenus.DronesTransportation.repositories.DroneRepository;
import com.elmenus.DronesTransportation.services.DroneService;
import com.elmenus.DronesTransportation.utils.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class DroneServiceImpl implements DroneService {
    private DroneRepository droneRepository;
    private DroneMapper droneMapper;
    private MedicationMapper medicationMapper;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository, DroneMapper droneMapper, MedicationMapper medicationMapper){
        this.droneRepository = droneRepository;
        this.droneMapper = droneMapper;
        this.medicationMapper = medicationMapper;
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
        throw new RecordNotFoundException("There is no Drone with serialNumber = " + serialNumber);
    }

    @Override
    public boolean isDroneExists(DroneDto droneDto) {
        return droneRepository.existsById(droneDto.getSerialNumber());
    }

    @Override
    public DroneDto save(DroneDto droneDto) {
        Drone drone = droneMapper.mapToEntity(droneDto);
        Optional<Drone> droneInDatabase = droneRepository.findById(droneDto.getSerialNumber());
        if(droneInDatabase.isEmpty()){
            // create it and make it idle by default when register a new drone
            drone.setState(StateEnum.IDLE);
        }else{
            if(drone.getBatteryCapacity() <= 25 && drone.getState() != StateEnum.IDLE){
                throw new RuntimeException("Cannot make the drone with state = " + drone.getState() + "with battery capacity = "+ drone.getBatteryCapacity());
            }
        }
        Drone savedDrone = droneRepository.save(drone);
        return droneMapper.mapToDto(savedDrone);
    }

    @Override
    public void deleteById(String serialNumber) {
        if(droneRepository.existsById(serialNumber)){
            droneRepository.deleteById(serialNumber);
            return;
        }
        throw new RecordNotFoundException("There is no Drone with serialNumber = " + serialNumber);
    }
}

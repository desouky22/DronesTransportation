package com.elmenus.DronesTransportation.services.impl;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;
import com.elmenus.DronesTransportation.errors.RecordNotFoundException;
import com.elmenus.DronesTransportation.mappers.DroneMapper;
import com.elmenus.DronesTransportation.mappers.MedicationMapper;
import com.elmenus.DronesTransportation.repositories.DroneRepository;
import com.elmenus.DronesTransportation.services.DroneService;
import com.elmenus.DronesTransportation.utils.StateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.rmi.server.LogStream.log;


@Service
@Slf4j
public class DroneServiceImpl implements DroneService {
    private DroneRepository droneRepository;
    private DroneMapper droneMapper;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository, DroneMapper droneMapper, MedicationMapper medicationMapper){
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
        throw new RecordNotFoundException("There is no Drone with serialNumber = " + serialNumber);
    }

    @Override
    public boolean isDroneExists(DroneDto droneDto) {
        return droneRepository.existsById(droneDto.getSerialNumber());
    }

    @Override
    public DroneDto save(DroneDto droneDto) {
        Drone drone = droneMapper.mapToEntity(droneDto);
        List<Drone> drones = droneRepository.findAll();
        Optional<Drone> droneInDatabase = droneRepository.findById(droneDto.getSerialNumber());
        Drone savedDrone = null;
        if(droneInDatabase.isEmpty()){
            // create it and make it idle by default when register a new drone
            drone.setState(StateEnum.IDLE);
            savedDrone = droneRepository.save(drone);
        }else{
            if(drone.getBatteryCapacity() <= 25 && drone.getState() != StateEnum.IDLE){
                throw new RuntimeException("Cannot make the drone with state = " + drone.getState() + " with battery capacity = "+ drone.getBatteryCapacity());
            }
            savedDrone = droneRepository.save(drone);
        }
        if(drones.size() > 10){
            throw new RuntimeException("you are allowed to register 10 Drones maximum");
        }
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

    @Override
    public List<DroneDto> getAvailableDrones() {
        List<Drone>  result = droneRepository.findByState(StateEnum.IDLE);
        return result.stream().map(drone -> droneMapper.mapToDto(drone)).collect(Collectors.toList());
    }

    @Scheduled(fixedRate = 5000)
    public void executeIt(){
        List<Drone> drones = droneRepository.findAll();
        log.info("===================================================================");
        int count = 1;
        for(Drone d: drones){
            log.info(count + " :" +
                    d.getState() + " " +
                    d.getSerialNumber() + " " +
                    d.getBatteryCapacity() + " " +
                    d.getModel() + " " +
                    d.getWeightLimit());
            count++;
        }
        log.info("===================================================================");
    }
}

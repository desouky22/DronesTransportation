package com.elmenus.DronesTransportation.services.impl;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;
import com.elmenus.DronesTransportation.domain.entities.Medication;
import com.elmenus.DronesTransportation.errors.RecordNotFoundException;
import com.elmenus.DronesTransportation.mappers.DroneMapper;
import com.elmenus.DronesTransportation.repositories.DroneRepository;
import com.elmenus.DronesTransportation.repositories.MedicationRepository;
import com.elmenus.DronesTransportation.services.DroneService;
import com.elmenus.DronesTransportation.utils.StateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class DroneServiceImpl implements DroneService {
    private DroneRepository droneRepository;
    private DroneMapper droneMapper;

    private MedicationRepository medicationRepository;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository, DroneMapper droneMapper, MedicationRepository medicationRepository){
        this.droneRepository = droneRepository;
        this.droneMapper = droneMapper;
        this.medicationRepository = medicationRepository;
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
        boolean isDroneExist = droneRepository.existsById(droneDto.getSerialNumber());
        Drone savedDrone = null;
        if(!isDroneExist){
            int dronesSize = droneRepository.findAll().size();
            if(dronesSize == 10){
                throw new RuntimeException("you are allowed to register 10 Drones maximum");
            }
            // when creating new Drone make its state IDLE
            drone.setState(StateEnum.IDLE);
        }else{
            if(drone.getBatteryCapacity() <= 25 && drone.getState() != StateEnum.IDLE){
                throw new RuntimeException("Cannot make the drone with state = " + drone.getState() + " with battery capacity = "+ drone.getBatteryCapacity());
            }
        }
        savedDrone = droneRepository.save(drone);
        return droneMapper.mapToDto(savedDrone);
    }

    @Override
    public void deleteById(String serialNumber) {
        if(droneRepository.existsById(serialNumber)){
            List<Medication> medicationList = medicationRepository.findByDroneId(serialNumber);
            for(Medication m: medicationList){
                m.setDroneId(null);
                medicationRepository.save(m);
            }
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

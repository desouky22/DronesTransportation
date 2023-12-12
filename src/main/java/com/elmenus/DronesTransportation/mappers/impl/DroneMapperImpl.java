package com.elmenus.DronesTransportation.mappers.impl;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;
import com.elmenus.DronesTransportation.mappers.DroneMapper;
import org.springframework.stereotype.Component;

@Component
public class DroneMapperImpl implements DroneMapper {
    @Override
    public DroneDto mapToDto(Drone drone) {
        if(drone == null) return null;

        return DroneDto.builder()
                .serialNumber(drone.getSerialNumber())
                .state(drone.getState())
                .model(drone.getModel())
                .batteryCapacity(drone.getBatteryCapacity())
                .weightLimit(drone.getWeightLimit())
                .build();
    }

    @Override
    public Drone mapToEntity(DroneDto droneDto) {
        if(droneDto == null) return null;

        return Drone.builder()
                .serialNumber(droneDto.getSerialNumber())
                .batteryCapacity(droneDto.getBatteryCapacity())
                .model(droneDto.getModel())
                .state(droneDto.getState())
                .weightLimit(droneDto.getWeightLimit())
                .build();
    }
}

package com.elmenus.DronesTransportation.mappers;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;
import org.mapstruct.Mapper;

@Mapper
public interface DroneMapper {
    DroneDto mapToDto(Drone drone);

    Drone mapToEntity(DroneDto droneDto);
}

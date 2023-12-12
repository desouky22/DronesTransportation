package com.elmenus.DronesTransportation.domain.dtos;

import com.elmenus.DronesTransportation.utils.ModelEnum;
import com.elmenus.DronesTransportation.utils.StateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DroneDto {
    private String serialNumber;
    private ModelEnum model;
    private Double weightLimit;
    private Integer batteryCapacity;
    private StateEnum state;
}

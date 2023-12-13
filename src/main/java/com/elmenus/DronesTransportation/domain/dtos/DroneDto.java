package com.elmenus.DronesTransportation.domain.dtos;

import com.elmenus.DronesTransportation.domain.entities.Medication;
import com.elmenus.DronesTransportation.utils.ModelEnum;
import com.elmenus.DronesTransportation.utils.StateEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DroneDto {
    @Size(min = 1, message = "{validation.name.size.too_short}")
    @Size(max = 100, message = "{validation.name.size.too_long}")
    @NotNull
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ModelEnum model;

    @DecimalMin(value = "0", message = "Please Enter a valid weight limit")
    @DecimalMax(value = "500", message = "Please Enter a valid weight limit")
    @NotNull
    private Double weightLimit;

    @Max(value = 100, message = "batteryCapacity cannot be more than 100")
    @Min(value = 0, message = "batteryCapacity cannot be less than 0")
    @NotNull
    private Integer batteryCapacity;

    @Enumerated(EnumType.STRING)
    @NotNull
    private StateEnum state;

    private List<Medication> medicationList;
}

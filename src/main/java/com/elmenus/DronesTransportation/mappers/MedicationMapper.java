package com.elmenus.DronesTransportation.mappers;

import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.domain.entities.Medication;

public interface MedicationMapper {
    MedicationDto mapToDto(Medication medication);

    Medication mapToEntity(MedicationDto medicationDto);
}


package com.elmenus.DronesTransportation.mappers.impl;

import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.domain.entities.Medication;
import com.elmenus.DronesTransportation.mappers.MedicationMapper;
import org.springframework.stereotype.Component;

@Component
public class MedicationMapperImpl implements MedicationMapper {
    @Override
    public MedicationDto mapToDto(Medication medication) {
        return MedicationDto.builder()
                .code(medication.getCode())
                .image(medication.getImage())
                .name(medication.getName())
                .weight(medication.getWeight())
                .build();
    }

    @Override
    public Medication mapToEntity(MedicationDto medicationDto) {
        return Medication.builder()
                .code(medicationDto.getCode())
                .image(medicationDto.getImage())
                .weight(medicationDto.getWeight())
                .name(medicationDto.getName())
                .build();
    }
}

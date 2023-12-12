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
                .id(medication.getId())
                .code(medication.getCode())
                .name(medication.getName())
                .weight(medication.getWeight())
                .image(medication.getImage())
                .build();
    }

    @Override
    public Medication mapToEntity(MedicationDto medicationDto) {
        return Medication.builder()
                .id(medicationDto.getId())
                .code(medicationDto.getCode())
                .weight(medicationDto.getWeight())
                .name(medicationDto.getName())
                .image(medicationDto.getImage())
                .build();
    }
}

package com.elmenus.DronesTransportation.services;

import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;

import java.util.List;
import java.util.Optional;

public interface MedicationService {
    Optional<MedicationDto> getMedicationById(Long id);
    List<MedicationDto> getAllMedications();
    MedicationDto createMedication(MedicationDto medicationDto);
}
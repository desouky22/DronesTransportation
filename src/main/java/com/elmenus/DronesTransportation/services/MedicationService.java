package com.elmenus.DronesTransportation.services;

import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;

import java.util.List;

public interface MedicationService {
    MedicationDto getMedicationById(Long id);
    List<MedicationDto> getAllMedications();
    MedicationDto createMedication(MedicationDto medicationDto);
    MedicationDto updateMedication(MedicationDto medicationDto);

    void deleteMedicationById(Long id);
    List<MedicationDto> findByDroneId(String serialNumber);
}
package com.elmenus.DronesTransportation.services;

import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;

import java.util.List;

public interface MedicationService {
    List<MedicationDto> getAllMedications();
}
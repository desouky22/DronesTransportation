package com.elmenus.DronesTransportation.services.impl;

import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.domain.entities.Medication;
import com.elmenus.DronesTransportation.mappers.MedicationMapper;
import com.elmenus.DronesTransportation.repositories.MedicationRepository;
import com.elmenus.DronesTransportation.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicationServiceImpl implements MedicationService {
    private MedicationRepository medicationRepository;
    private MedicationMapper medicationMapper;

    @Autowired
    public MedicationServiceImpl(MedicationRepository medicationRepository, MedicationMapper medicationMapper){
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
    }

    @Override
    public List<MedicationDto> getAllMedications() {
        List<Medication> result = medicationRepository.findAll();
        return  result.stream().map(medication -> medicationMapper.mapToDto(medication)).toList();
    }


    @Override
    public Optional<MedicationDto> getMedicationById(Long id) {
        Optional<Medication> result = medicationRepository.findById(id);
        return result.map(medication -> medicationMapper.mapToDto(medication));
    }

    @Override
    public MedicationDto createMedication(MedicationDto medicationDto) {
        Medication medication = medicationMapper.mapToEntity(medicationDto);
        Medication result = medicationRepository.save(medication);
        return medicationMapper.mapToDto(result);
    }
}

package com.elmenus.DronesTransportation.services.impl;

import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.domain.entities.Medication;
import com.elmenus.DronesTransportation.errors.DuplicateException;
import com.elmenus.DronesTransportation.errors.RecordNotFoundException;
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
    public MedicationDto getMedicationById(Long id) {
        Optional<Medication> result = medicationRepository.findById(id);
        if(result.isPresent())
            return medicationMapper.mapToDto(result.get());

        throw new RecordNotFoundException("There is no medication with ID = " + id);
    }

    @Override
    public MedicationDto createMedication(MedicationDto medicationDto) {
        String code = medicationDto.getCode();
        if(medicationRepository.findByCode(code).isPresent()){
            throw new DuplicateException("There is another medication with code = " + code);
        }
        Medication medication = medicationMapper.mapToEntity(medicationDto);
        Medication result = medicationRepository.save(medication);
        return medicationMapper.mapToDto(result);
    }

    @Override
    public MedicationDto updateMedication(MedicationDto medicationDto) {
        Optional<Medication> medication = medicationRepository.findById(medicationDto.getId());
        if(medication.isPresent()){
            Medication updatedMedication = medicationMapper.mapToEntity(medicationDto);
            Medication saved = medicationRepository.save(updatedMedication);
            return medicationMapper.mapToDto(saved);
        }
        throw new RecordNotFoundException("There is no Medication with ID = " + medicationDto.getId());
    }

    @Override
    public void deleteMedicationById(Long id) {
        if(medicationRepository.existsById(id)){
            medicationRepository.deleteById(id);
            return;
        }
        throw new RecordNotFoundException("There is no medication with ID = " + id);
    }

    @Override
    public List<MedicationDto> findByDroneId(String serialNumber) {
        List<Medication> result = medicationRepository.findByDroneId(serialNumber);
        return result.stream().map(medication ->
            medicationMapper.mapToDto(medication)
        ).collect(Collectors.toList());
    }
}

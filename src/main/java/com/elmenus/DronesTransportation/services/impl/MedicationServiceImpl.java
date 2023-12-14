package com.elmenus.DronesTransportation.services.impl;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;
import com.elmenus.DronesTransportation.domain.entities.Medication;
import com.elmenus.DronesTransportation.errors.DuplicateException;
import com.elmenus.DronesTransportation.errors.RecordNotFoundException;
import com.elmenus.DronesTransportation.mappers.MedicationMapper;
import com.elmenus.DronesTransportation.repositories.MedicationRepository;
import com.elmenus.DronesTransportation.services.DroneService;
import com.elmenus.DronesTransportation.services.MedicationService;
import com.elmenus.DronesTransportation.utils.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicationServiceImpl implements MedicationService {
    private MedicationRepository medicationRepository;
    private MedicationMapper medicationMapper;

    private DroneService droneService;

    @Autowired
    public MedicationServiceImpl(MedicationRepository medicationRepository, MedicationMapper medicationMapper, DroneService droneService){
        this.medicationRepository = medicationRepository;
        this.medicationMapper = medicationMapper;
        this.droneService = droneService;
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
        Optional<Medication> medicationWithCode = medicationRepository.findByCode(code);
        if(medicationWithCode.isPresent()){
            throw new DuplicateException("There is another medication with code = " + code);
        }
        if(medicationDto.getDroneId() != null) {
            DroneDto drone = droneService.getDroneById(medicationDto.getDroneId());
            if(drone.getState() != StateEnum.IDLE){
                throw new RuntimeException("cannot assign medication with ID = " + medicationDto.getId() + " to a drone that has a state of " + drone.getState());
            }
            Double totalWeight = 0.0;
            for(Medication m: drone.getMedicationList()){
                totalWeight += m.getWeight();
            }
            if(totalWeight + medicationDto.getWeight() > drone.getWeightLimit()){
                throw new RuntimeException("Cannot add this medication to this drone because of the weight limit");
            }
        }

        Medication mappedMedication = medicationMapper.mapToEntity(medicationDto);
        Medication result = medicationRepository.save(mappedMedication);
        return medicationMapper.mapToDto(result);
    }

    @Override
    public MedicationDto updateMedication(MedicationDto medicationDto) {
        String code = medicationDto.getCode();
        Optional<Medication> medication = medicationRepository.findById(medicationDto.getId());
        if(medication.isEmpty()){
            throw new RecordNotFoundException("There is no medication with ID = " + medicationDto.getId());
        }
        Optional<Medication> medicationWithCode = medicationRepository.findByCode(code);
        if(medicationWithCode.isPresent() && !Objects.equals(medicationWithCode.get().getId(), medicationDto.getId())){
            throw new DuplicateException("There is another medication with code = " + code);
        }
        if(medicationDto.getDroneId() != null) {
            DroneDto drone = droneService.getDroneById(medicationDto.getDroneId());
            if(drone.getState() != StateEnum.IDLE){
                throw new RuntimeException("cannot assign medication with ID = " + medicationDto.getId() + " to a drone that has a state of " + drone.getState());
            }
            Double totalWeight = 0.0;
            for(Medication m: drone.getMedicationList()){
                totalWeight += m.getWeight();
            }
            if(totalWeight + medicationDto.getWeight() > drone.getWeightLimit()){
                throw new RuntimeException("Cannot add this medication to this drone because of the weight limit");
            }
        }
        Medication updatedMedication = medicationMapper.mapToEntity(medicationDto);
        Medication saved = medicationRepository.save(updatedMedication);
        return medicationMapper.mapToDto(saved);
    }

    @Override
    public void deleteMedicationById(Long id) {
        Optional<Medication> medication = medicationRepository.findById(id);
        if(medication.isPresent()){
            String droneSerialNumber = medication.get().getDroneId();
            DroneDto droneDto = droneService.getDroneById(droneSerialNumber);
            List<Medication> medications =  droneDto.getMedicationList();
            medications.remove(medication.get());
            droneDto.setMedicationList(medications);
            droneService.save(droneDto);
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

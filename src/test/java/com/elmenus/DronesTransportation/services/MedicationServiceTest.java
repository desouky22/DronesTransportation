package com.elmenus.DronesTransportation.services;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.domain.entities.Medication;
import com.elmenus.DronesTransportation.errors.DuplicateException;
import com.elmenus.DronesTransportation.errors.RecordNotFoundException;
import com.elmenus.DronesTransportation.utils.DroneTestUtils;
import com.elmenus.DronesTransportation.utils.MedicationTestUtils;
import com.elmenus.DronesTransportation.mappers.impl.MedicationMapperImpl;
import com.elmenus.DronesTransportation.repositories.MedicationRepository;
import com.elmenus.DronesTransportation.services.impl.DroneServiceImpl;
import com.elmenus.DronesTransportation.services.impl.MedicationServiceImpl;
import com.elmenus.DronesTransportation.utils.StateEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest
public class MedicationServiceTest {
    @Mock
    private MedicationRepository medicationRepository;

    @Mock
    private MedicationMapperImpl medicationMapper;

    @Mock
    private DroneServiceImpl droneService;

    @InjectMocks
    private MedicationServiceImpl underTest;

    @Test
    public void getAllMedicationsTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();
        List<Medication> medicationListA = MedicationTestUtils.createMedicationListA();
        List<MedicationDto> medicationDtoListA = MedicationTestUtils.createMedicationDtoListA();

        Mockito.when(medicationRepository.findAll()).thenReturn(medicationListA);
        Mockito.when(medicationMapper.mapToDto(medicationA)).thenReturn(medicationDtoA);

        List<MedicationDto> actual = underTest.getAllMedications();

        Assertions.assertIterableEquals(medicationDtoListA, actual);
    }

    @Test
    public void getMedicationByIdExistTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();

        Mockito.when(medicationRepository.findById(medicationA.getId())).thenReturn(Optional.of(medicationA));
        Mockito.when(medicationMapper.mapToDto(medicationA)).thenReturn(medicationDtoA);

        MedicationDto actual = underTest.getMedicationById(medicationDtoA.getId());

        Assertions.assertEquals(medicationDtoA, actual);
    }

    @Test
    public void getMedicationByIdNotExistTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();

        Mockito.when(medicationRepository.findById(medicationA.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(RecordNotFoundException.class,() -> underTest.getMedicationById(medicationDtoA.getId()));
    }

    @Test
    public void createInValidMedicationWithExistCodeTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();

        Mockito.when(medicationRepository.findByCode(Mockito.anyString())).thenReturn(Optional.of(medicationA));

        Assertions.assertThrows(DuplicateException.class, () -> underTest.createMedication(medicationDtoA));
    }

    @Test
    public void createValidMedicationWithUniqueCodeAndWithNoAssignToDroneTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();
        medicationDtoA.setDroneId(null);
        medicationA.setDroneId(null);

        Mockito.when(medicationRepository.findByCode(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(medicationRepository.save(medicationA)).thenReturn(medicationA);
        Mockito.when(medicationMapper.mapToEntity(medicationDtoA)).thenReturn(medicationA);
        Mockito.when(medicationMapper.mapToDto(medicationA)).thenReturn(medicationDtoA);

        MedicationDto actual = underTest.createMedication(medicationDtoA);

        Assertions.assertEquals(medicationDtoA, actual);
    }

    @Test
    public void createValidMedicationWithUniqueCodeAndWithAssignToDroneNotIdleTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();

        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();
        droneDtoA.setState(StateEnum.LOADING);

        Mockito.when(droneService.getDroneById(medicationA.getDroneId())).thenReturn(droneDtoA);
        Mockito.when(medicationRepository.findByCode(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(medicationRepository.save(medicationA)).thenReturn(medicationA);
        Mockito.when(medicationMapper.mapToEntity(medicationDtoA)).thenReturn(medicationA);
        Mockito.when(medicationMapper.mapToDto(medicationA)).thenReturn(medicationDtoA);

        Assertions.assertThrows(RuntimeException.class, ()-> underTest.createMedication(medicationDtoA));
    }

    @Test
    public void createValidMedicationWithUniqueCodeAndWithAssignToDroneThatIdleButDoNotHasEnoughWeightTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();
        medicationDtoA.setWeight(500d);
        medicationA.setWeight(500d);

        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();

        Mockito.when(droneService.getDroneById(medicationA.getDroneId())).thenReturn(droneDtoA);
        Mockito.when(medicationRepository.findByCode(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(medicationRepository.save(medicationA)).thenReturn(medicationA);
        Mockito.when(medicationMapper.mapToEntity(medicationDtoA)).thenReturn(medicationA);
        Mockito.when(medicationMapper.mapToDto(medicationA)).thenReturn(medicationDtoA);

        Assertions.assertThrows(RuntimeException.class, ()-> underTest.createMedication(medicationDtoA));
    }

    @Test
    public void createValidMedicationWithUniqueCodeAndWithAssignToDroneThatIdleAndHasEnoughWeightTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();
        // there is another medication on this drone with weight = 10
        // so tha max weight we can make this medication equal is 390 (400 {drone max weight} - 10)
        medicationDtoA.setWeight(390d);

        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();

        Mockito.when(droneService.getDroneById(medicationA.getDroneId())).thenReturn(droneDtoA);
        Mockito.when(medicationRepository.findByCode(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(medicationRepository.save(medicationA)).thenReturn(medicationA);
        Mockito.when(medicationMapper.mapToEntity(medicationDtoA)).thenReturn(medicationA);
        Mockito.when(medicationMapper.mapToDto(medicationA)).thenReturn(medicationDtoA);

        MedicationDto actual = underTest.createMedication(medicationDtoA);

        Assertions.assertEquals(medicationDtoA, actual);
    }

    @Test
    public void updateMedicationThatDoesNotExistTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();

        Mockito.when(medicationRepository.findById(medicationA.getId())).thenReturn(Optional.empty());

        Assertions.assertThrows(RecordNotFoundException.class, () -> underTest.updateMedication(medicationDtoA));
    }

    @Test
    public void updateMedicationWithCodeThatExistTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();
        Medication medicationB = MedicationTestUtils.createMedicationB();
        medicationB.setCode(medicationA.getCode());

        Mockito.when(medicationRepository.findById(medicationA.getId())).thenReturn(Optional.of(medicationA));
        Mockito.when(medicationRepository.findByCode(medicationA.getCode())).thenReturn(Optional.of(medicationB));

        Assertions.assertThrows(DuplicateException.class, () -> underTest.updateMedication(medicationDtoA));
    }

    @Test
    public void updateMedicationWithCodeThatNotExistAndWithDroneThatIsNotIdleTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();

        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();
        medicationA.setDroneId(droneDtoA.getSerialNumber());

        droneDtoA.setState(StateEnum.LOADING);


        Mockito.when(medicationRepository.findById(medicationA.getId())).thenReturn(Optional.of(medicationA));
        Mockito.when(medicationRepository.findByCode(medicationA.getCode())).thenReturn(Optional.empty());
        Mockito.when(droneService.getDroneById(droneDtoA.getSerialNumber())).thenReturn(droneDtoA);

        Assertions.assertThrows(RuntimeException.class, () -> underTest.updateMedication(medicationDtoA));
    }

    @Test
    public void updateMedicationWithCodeThatNotExistAndWithDroneThatIsIdleButDoNotHasEnoughWeightTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();
        medicationDtoA.setWeight(400d);

        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();
        medicationA.setDroneId(droneDtoA.getSerialNumber());
        droneDtoA.setState(StateEnum.IDLE);

        Mockito.when(medicationRepository.findById(medicationA.getId())).thenReturn(Optional.of(medicationA));
        Mockito.when(medicationRepository.findByCode(medicationA.getCode())).thenReturn(Optional.empty());
        Mockito.when(droneService.getDroneById(droneDtoA.getSerialNumber())).thenReturn(droneDtoA);

        Assertions.assertThrows(RuntimeException.class, () -> underTest.updateMedication(medicationDtoA));
    }

    @Test
    public void updateMedicationWithCodeThatNotExistAndWithDroneThatIsIdleAndHasEnoughWeightTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();
        medicationDtoA.setWeight(390d);
        medicationA.setWeight(390d);

        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();
        medicationA.setDroneId(droneDtoA.getSerialNumber());
        droneDtoA.setState(StateEnum.IDLE);

        Mockito.when(medicationRepository.findById(medicationA.getId())).thenReturn(Optional.of(medicationA));
        Mockito.when(medicationRepository.findByCode(medicationA.getCode())).thenReturn(Optional.empty());
        Mockito.when(medicationRepository.save(medicationA)).thenReturn(medicationA);
        Mockito.when(droneService.getDroneById(droneDtoA.getSerialNumber())).thenReturn(droneDtoA);
        Mockito.when(medicationMapper.mapToDto(medicationA)).thenReturn(medicationDtoA);
        Mockito.when(medicationMapper.mapToEntity(medicationDtoA)).thenReturn(medicationA);

        MedicationDto actual = underTest.updateMedication(medicationDtoA);

        Assertions.assertEquals(medicationDtoA, actual);
    }

    @Test
    public void deleteMedicationByIdNotExistTest(){
        Mockito.when(medicationRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(RecordNotFoundException.class, ()->underTest.deleteMedicationById(1L));
    }

    @Test
    public void deleteMedicationByIdExistTest(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();

        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();
        List<Medication> medicationListA = MedicationTestUtils.createMedicationListA();
        droneDtoA.setMedicationList(medicationListA);
        medicationA.setDroneId(droneDtoA.getSerialNumber());
        medicationDtoA.setDroneId(droneDtoA.getSerialNumber());

        Mockito.when(medicationRepository.findById(medicationDtoA.getId())).thenReturn(Optional.of(medicationA));
        Mockito.when(droneService.getDroneById(medicationDtoA.getDroneId())).thenReturn(droneDtoA);
        Mockito.when(droneService.save(droneDtoA)).thenReturn(droneDtoA);

        underTest.deleteMedicationById(medicationA.getId());

        Mockito.verify(medicationRepository, Mockito.times(1)).deleteById(medicationA.getId());
    }

    @Test
    public void findMedicationsByDroneId(){
        Medication medicationA = MedicationTestUtils.createMedicationA();
        MedicationDto medicationDtoA = MedicationTestUtils.createMedicationDtoA();

        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();
        List<Medication> medicationListA = MedicationTestUtils.createMedicationListA();


        Mockito.when(medicationRepository.findByDroneId(droneDtoA.getSerialNumber())).thenReturn(medicationListA);
        Mockito.when(medicationMapper.mapToDto(medicationA)).thenReturn(medicationDtoA);
        Mockito.when(medicationMapper.mapToEntity(medicationDtoA)).thenReturn(medicationA);

        List<MedicationDto> actual = underTest.findByDroneId(droneDtoA.getSerialNumber());
        List<MedicationDto> expected = medicationListA.stream().map(medication -> medicationMapper.mapToDto(medication)).toList();

        Assertions.assertEquals(expected, actual);

    }
}

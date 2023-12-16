package com.elmenus.DronesTransportation.services;

import com.elmenus.DronesTransportation.utils.DroneTestUtils;
import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;
import com.elmenus.DronesTransportation.errors.RecordNotFoundException;
import com.elmenus.DronesTransportation.mappers.impl.DroneMapperImpl;
import com.elmenus.DronesTransportation.repositories.DroneRepository;
import com.elmenus.DronesTransportation.services.impl.DroneServiceImpl;
import com.elmenus.DronesTransportation.utils.ModelEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DroneServiceTest {
    @Mock
    private DroneRepository droneRepository;

    @Mock
    private DroneMapperImpl droneMapper;

    @InjectMocks
    private DroneServiceImpl underTest;

    @Test
    public void findAllDronesTest(){
        Drone droneA = DroneTestUtils.createDroneA();
        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();
        List<Drone> dronesA = DroneTestUtils.createDroneListA();
        List<DroneDto> dronesDtoA = DroneTestUtils.createDroneDtoListA();

        Mockito.when(droneRepository.findAll()).thenReturn(dronesA);
        Mockito.when(droneMapper.mapToDto(droneA)).thenReturn(droneDtoA);

        List<DroneDto> actual = underTest.getAllDrones();

        Assertions.assertIterableEquals(dronesDtoA, actual);
    }

    @Test
    public void findDroneByIdTest(){
        Drone drone = DroneTestUtils.createDroneA();
        DroneDto droneDto = DroneTestUtils.createDroneDtoA();

        Mockito.when(droneRepository.findById(Mockito.anyString())).thenReturn(Optional.of(drone));
        Mockito.when(droneMapper.mapToDto(drone)).thenReturn(droneDto);

        DroneDto actual = underTest.getDroneById("1");

        Assertions.assertEquals(actual, droneDto);
    }

    @Test
    public void findDroneByIdNotFoundTest(){
        Mockito.when(droneRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(RecordNotFoundException.class, () -> underTest.getDroneById("1"));
    }

    @Test
    public void isDroneExistReturnsTrueTest(){
        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();

        Mockito.when(droneRepository.existsById(droneDtoA.getSerialNumber())).thenReturn(true);

        boolean isDroneExist = underTest.isDroneExists(droneDtoA);

        Assertions.assertTrue(isDroneExist);
    }

    @Test
    public void isDroneExistReturnsFalseTest(){
        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();

        Mockito.when(droneRepository.existsById(droneDtoA.getSerialNumber())).thenReturn(false);

        boolean isDroneExist = underTest.isDroneExists(droneDtoA);

        Assertions.assertFalse(isDroneExist);
    }

    @Test
    public void createValidDroneTest(){
        // valid drone
        Drone drone = DroneTestUtils.createDroneA();
        DroneDto droneDto = DroneTestUtils.createDroneDtoA();

        Mockito.when(droneRepository.save(drone)).thenReturn(drone);
        Mockito.when(droneMapper.mapToDto(drone)).thenReturn(droneDto);
        Mockito.when(droneMapper.mapToEntity(droneDto)).thenReturn(drone);

        DroneDto actual = underTest.save(droneDto);

        Assertions.assertEquals(droneDto, actual);
    }

    @Test
    public void createInValidDroneAfterCreatingTenDronesBeforeItTest(){
        Drone drone = DroneTestUtils.createDroneA();
        DroneDto droneDto = DroneTestUtils.createDroneDtoA();

        Mockito.when(droneRepository.existsById(drone.getSerialNumber())).thenReturn(false);
        Mockito.when(droneRepository.findAll()).thenReturn(DroneTestUtils.createDroneListWithSizeEqualToTen());
        Mockito.when(droneMapper.mapToEntity(droneDto)).thenReturn(drone);

        Assertions.assertThrows(RuntimeException.class, () -> underTest.save(droneDto));
    }


    @Test
    public void updateValidDroneTest(){
        Drone droneA = DroneTestUtils.createDroneA();
        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();
        Drone updatedDroneB = DroneTestUtils.createDroneB();
        DroneDto updatedDroneDtoB = DroneTestUtils.createDroneDtoB();

        Mockito.when(droneRepository.existsById(droneA.getSerialNumber())).thenReturn(true);
        Mockito.when(droneRepository.save(updatedDroneB)).thenReturn(updatedDroneB);
        Mockito.when(droneMapper.mapToDto(updatedDroneB)).thenReturn(updatedDroneDtoB);
        Mockito.when(droneMapper.mapToEntity(updatedDroneDtoB)).thenReturn(updatedDroneB);

        updatedDroneDtoB.setSerialNumber(droneA.getSerialNumber());
        updatedDroneDtoB.setModel(ModelEnum.HEAVY_WEIGHT);

        updatedDroneDtoB = underTest.save(updatedDroneDtoB);

        Assertions.assertNotEquals(updatedDroneDtoB, droneDtoA);
    }

    @Test
    public void updateInValidDroneTest(){
        Drone droneA = DroneTestUtils.createDroneA();
        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();
        Drone updatedDroneB = DroneTestUtils.createDroneB();
        DroneDto updatedDroneDtoB = DroneTestUtils.createDroneDtoB();

        Mockito.when(droneRepository.existsById(droneDtoA.getSerialNumber())).thenReturn(true);
        Mockito.when(droneRepository.save(droneA)).thenReturn(droneA);
        Mockito.when(droneMapper.mapToDto(droneA)).thenReturn(droneDtoA);
        Mockito.when(droneMapper.mapToEntity(droneDtoA)).thenReturn(droneA);
        Mockito.when(droneMapper.mapToDto(updatedDroneB)).thenReturn(updatedDroneDtoB);
        Mockito.when(droneMapper.mapToEntity(updatedDroneDtoB)).thenReturn(updatedDroneB);

        Assertions.assertThrows(RuntimeException.class, () -> underTest.save(updatedDroneDtoB));
    }


    @Test
    public void deleteByIdExistTest(){
        String serialNumber = "1";

        Mockito.when(droneRepository.existsById(serialNumber)).thenReturn(true);

        underTest.deleteById(serialNumber);

        Mockito.verify(droneRepository, Mockito.times(1)).deleteById(serialNumber);
    }


    @Test
    public void deleteByIdNotExistTest(){
        String serialNumber = "1";

        Mockito.when(droneRepository.existsById(serialNumber)).thenReturn(false);

        Assertions.assertThrows(RecordNotFoundException.class, () -> underTest.deleteById("1"));
    }

    @Test
    public void getAvailableDronesTest(){
        List<DroneDto> droneDtoListA = DroneTestUtils.createDroneDtoListA();
        List<Drone> droneListA = DroneTestUtils.createDroneListA();
        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();
        Drone droneA = DroneTestUtils.createDroneA();

        Mockito.when(droneRepository.findByState(Mockito.any())).thenReturn(droneListA);
        Mockito.when(droneMapper.mapToDto(droneA)).thenReturn(droneDtoA);

        List<DroneDto> actual = underTest.getAvailableDrones();

        Assertions.assertIterableEquals(droneDtoListA, actual);
    }
}

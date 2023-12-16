package com.elmenus.DronesTransportation.utils;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;

import java.util.ArrayList;
import java.util.List;

public class DroneTestUtils {
    public static DroneDto createDroneDtoA(){
        return DroneDto.builder()
                .state(StateEnum.IDLE)
                .model(ModelEnum.LIGHT_WEIGHT)
                .batteryCapacity(100)
                .serialNumber("1")
                .medicationList(MedicationTestUtils.createMedicationListA())
                .weightLimit(400d)
                .build();
    }

    public static DroneDto createDroneDtoB(){
        return DroneDto.builder()
                .state(StateEnum.LOADED)
                .model(ModelEnum.CRUISER_WEIGHT)
                .batteryCapacity(100)
                .serialNumber("2")
                .medicationList(MedicationTestUtils.createMedicationListB())
                .weightLimit(400d)
                .build();
    }

    public static Drone createDroneA(){
        return Drone.builder()
                .state(StateEnum.LOADED)
                .model(ModelEnum.CRUISER_WEIGHT)
                .batteryCapacity(100)
                .serialNumber("1")
                .medicationList(MedicationTestUtils.createMedicationListB())
                .weightLimit(400d)
                .build();
    }

    public static Drone createDroneB(){
        return Drone.builder()
                .state(StateEnum.LOADED)
                .model(ModelEnum.CRUISER_WEIGHT)
                .batteryCapacity(100)
                .serialNumber("2")
                .medicationList(MedicationTestUtils.createMedicationListB())
                .weightLimit(400d)
                .build();
    }

    public static List<DroneDto> createDroneDtoListA(){
        List<DroneDto> droneDtoList = new ArrayList<>();
        droneDtoList.add(createDroneDtoA());
        return droneDtoList;
    }

    public static List<DroneDto> createDroneDtoListB(){
        List<DroneDto> droneDtoList = new ArrayList<>();
        droneDtoList.add(createDroneDtoB());
        return droneDtoList;
    }

    public static List<Drone> createDroneListA(){
        List<Drone> droneDtoList = new ArrayList<>();
        droneDtoList.add(createDroneA());
        return droneDtoList;
    }

    public static List<Drone> createDroneListB(){
        List<Drone> droneDtoList = new ArrayList<>();
        droneDtoList.add(createDroneB());
        return droneDtoList;
    }

    public static List<Drone> createDroneListWithSizeEqualToTen(){
        List<Drone> drones = new ArrayList<>();
        drones.add(new Drone("1",ModelEnum.CRUISER_WEIGHT,500d,100,StateEnum.IDLE, null));
        drones.add(new Drone("2",ModelEnum.CRUISER_WEIGHT,500d,100,StateEnum.IDLE, null));
        drones.add(new Drone("3",ModelEnum.CRUISER_WEIGHT,500d,100,StateEnum.IDLE, null));
        drones.add(new Drone("4",ModelEnum.CRUISER_WEIGHT,500d,100,StateEnum.IDLE, null));
        drones.add(new Drone("5",ModelEnum.CRUISER_WEIGHT,500d,100,StateEnum.IDLE, null));
        drones.add(new Drone("6",ModelEnum.CRUISER_WEIGHT,500d,100,StateEnum.IDLE, null));
        drones.add(new Drone("7",ModelEnum.CRUISER_WEIGHT,500d,100,StateEnum.IDLE, null));
        drones.add(new Drone("8",ModelEnum.CRUISER_WEIGHT,500d,100,StateEnum.IDLE, null));
        drones.add(new Drone("9",ModelEnum.CRUISER_WEIGHT,500d,100,StateEnum.IDLE, null));
        drones.add(new Drone("10",ModelEnum.CRUISER_WEIGHT,500d,100,StateEnum.IDLE, null));

        return drones;
    }
}

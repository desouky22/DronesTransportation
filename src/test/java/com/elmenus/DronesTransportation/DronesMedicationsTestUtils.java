package com.elmenus.DronesTransportation;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;
import com.elmenus.DronesTransportation.domain.entities.Medication;
import com.elmenus.DronesTransportation.utils.ModelEnum;
import com.elmenus.DronesTransportation.utils.StateEnum;

import java.util.ArrayList;
import java.util.List;

public class DronesMedicationsTestUtils {

    public static DroneDto createDroneDtoA(){
        return DroneDto.builder()
                .state(StateEnum.IDLE)
                .model(ModelEnum.LIGHT_WEIGHT)
                .batteryCapacity(100)
                .serialNumber("1")
                .medicationList(createMedicationListA())
                .weightLimit(400d)
                .build();
    }

    public static DroneDto createDroneDtoB(){
        return DroneDto.builder()
                .state(StateEnum.LOADED)
                .model(ModelEnum.CRUISER_WEIGHT)
                .batteryCapacity(100)
                .serialNumber("2")
                .medicationList(createMedicationListB())
                .weightLimit(400d)
                .build();
    }

    public static Drone createDroneA(){
        return Drone.builder()
                .state(StateEnum.LOADED)
                .model(ModelEnum.CRUISER_WEIGHT)
                .batteryCapacity(100)
                .serialNumber("1")
                .medicationList(createMedicationListB())
                .weightLimit(400d)
                .build();
    }

    public static Drone createDroneB(){
        return Drone.builder()
                .state(StateEnum.LOADED)
                .model(ModelEnum.CRUISER_WEIGHT)
                .batteryCapacity(100)
                .serialNumber("2")
                .medicationList(createMedicationListB())
                .weightLimit(400d)
                .build();
    }

    public static MedicationDto createMedicationDtoA(){
        return MedicationDto.builder()
                .droneId("1")
                .id(1L)
                .name("name1")
                .code("CODE1")
                .weight(10d)
                .build();
    }

    public static MedicationDto createMedicationDtoB(){
        return MedicationDto.builder()
                .droneId("2")
                .id(2L)
                .name("name2")
                .code("CODE2")
                .weight(20d)
                .build();
    }

    public static Medication createMedicationA(){
        return Medication.builder()
                .droneId("1")
                .id(1L)
                .name("name1")
                .code("CODE1")
                .weight(10d)
                .build();
    }

    public static Medication createMedicationB(){
        return Medication.builder()
                .droneId("2")
                .id(2L)
                .name("name2")
                .code("CODE2")
                .weight(20d)
                .build();
    }


    public static List<Medication> createMedicationListA(){
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(createMedicationA());
        return medicationList;
    }

    public static List<Medication> createMedicationListB(){
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(createMedicationB());
        return medicationList;
    }

    public static List<MedicationDto> createMedicationDtoListA(){
        List<MedicationDto> medicationList = new ArrayList<>();
        medicationList.add(createMedicationDtoA());
        return medicationList;
    }

    public static List<MedicationDto> createMedicationDtoListB(){
        List<MedicationDto> medicationList = new ArrayList<>();
        medicationList.add(createMedicationDtoB());
        return medicationList;
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

package com.elmenus.DronesTransportation.utils;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;
import com.elmenus.DronesTransportation.domain.entities.Medication;

import java.util.ArrayList;
import java.util.List;

public class MedicationTestUtils {
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
}

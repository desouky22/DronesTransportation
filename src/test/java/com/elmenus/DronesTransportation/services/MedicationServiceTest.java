package com.elmenus.DronesTransportation.services;

import com.elmenus.DronesTransportation.utils.MedicationTestUtils;
import com.elmenus.DronesTransportation.mappers.impl.MedicationMapperImpl;
import com.elmenus.DronesTransportation.repositories.MedicationRepository;
import com.elmenus.DronesTransportation.services.impl.DroneServiceImpl;
import com.elmenus.DronesTransportation.services.impl.MedicationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

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


//    @Test
//    public void getAllMedicationsTest(){
//        Mockito.when(medicationRepository.findAll()).thenReturn(MedicationTestUtils.)
//    }


}

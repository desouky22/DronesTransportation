package com.elmenus.DronesTransportation.controllers;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.utils.DroneTestUtils;
import com.elmenus.DronesTransportation.utils.ModelEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class DroneControllerIT {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public DroneControllerIT(MockMvc mockMvc, ObjectMapper objectMapper){
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Test
    public void getAllDronesTest(){
        mockMvc
                .perform(MockMvcRequestBuilders.get("/drones"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getDroneByIdExistTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/drones/9"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getDroneByIdNotExistTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/drones/10"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void createDroneTest() throws Exception {
        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();
        droneDtoA.setSerialNumber("10");
        String DroneDtoJson = objectMapper.writeValueAsString(droneDtoA);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/drones/10").content(DroneDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serialNumber").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.weightLimit").isNumber());
    }

    @Test
    public void updateDroneTest() throws Exception{
        DroneDto droneDtoA = DroneTestUtils.createDroneDtoA();
        droneDtoA.setSerialNumber("1");
        droneDtoA.setModel(ModelEnum.HEAVY_WEIGHT);

        String DroneDtoJson = objectMapper.writeValueAsString(droneDtoA);
        mockMvc
                .perform(MockMvcRequestBuilders.put("/drones/1").content(DroneDtoJson).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.serialNumber").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.weightLimit").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.model").value(ModelEnum.HEAVY_WEIGHT.toString()));

    }

    @Test
    public void deleteDroneByIdNotExistTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/drones/11"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteDroneByIdExistTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/drones/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void getAllMedicationsOnDroneById() throws Exception{
        mockMvc
                .perform(MockMvcRequestBuilders.get("/drones/1/medications"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getAllAvailableDrones() throws Exception{
        // aka get drones with state = IDLE
        mockMvc
                .perform(MockMvcRequestBuilders.get("/drones/available"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}

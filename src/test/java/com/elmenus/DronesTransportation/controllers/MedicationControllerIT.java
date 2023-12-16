package com.elmenus.DronesTransportation.controllers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class MedicationControllerIT {
    private final MockMvc mockMvc;

    @Autowired
    public MedicationControllerIT(MockMvc mockMvc ){
        this.mockMvc = mockMvc;
    }

    @SneakyThrows
    @Test
    public void getAllMedicationsTest(){
        mockMvc
                .perform(MockMvcRequestBuilders.get("/medications"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getMedicationByIdExistTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/medications/9"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getMedicationByIdNotExistTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/medications/10"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteMedicationByIdNotExistTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/medications/11"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deleteMedicationByIdExistTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.delete("/medications/2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}

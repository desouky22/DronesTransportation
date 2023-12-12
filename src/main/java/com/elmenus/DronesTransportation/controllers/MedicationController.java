package com.elmenus.DronesTransportation.controllers;

import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.services.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/medications")
public class MedicationController {
    private MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService){
        this.medicationService = medicationService;
    }

    @GetMapping()
    public ResponseEntity<List<MedicationDto>> getAllMedications(){
        List<MedicationDto> result = medicationService.getAllMedications();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

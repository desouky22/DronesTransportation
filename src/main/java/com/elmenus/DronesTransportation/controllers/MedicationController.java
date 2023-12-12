package com.elmenus.DronesTransportation.controllers;

import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.services.MedicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> getMedicationById(@PathVariable Long id){
        Optional<MedicationDto> result = medicationService.getMedicationById(id);
        if(result.isPresent()){
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("There is no medication with this ID", HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<MedicationDto> createMedication(@RequestBody @Valid MedicationDto medicationDto){
        MedicationDto savedMedication = medicationService.createMedication(medicationDto);
        return new ResponseEntity<>(savedMedication, HttpStatus.CREATED);
    }
}

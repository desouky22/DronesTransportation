package com.elmenus.DronesTransportation.controllers;

import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.services.MedicationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
        MedicationDto result = medicationService.getMedicationById(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(path = "", method = POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MedicationDto> createMedication(@RequestPart MultipartFile image,
                                   @RequestPart @Pattern(regexp = "^[a-zA-Z0-9_-]+$") @NotBlank String name,
                                   @RequestPart @Pattern(regexp = "^[A-Z0-9_]+$") @NotBlank String code,
                                   @RequestPart @Pattern(regexp = "^[+]?[0-9]*\\.?[0-9]*$") @NotBlank String weight
                                   ) throws IOException {
        MedicationDto medicationDto = MedicationDto.builder()
                .image(image.getBytes())
                .code(code)
                .weight(Double.valueOf(weight))
                .name(name)
                .build();

        MedicationDto savedMedication = medicationService.createMedication(medicationDto);
        return new ResponseEntity<>(savedMedication, HttpStatus.CREATED);
    }
}

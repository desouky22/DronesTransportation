package com.elmenus.DronesTransportation.controllers;

import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.services.MedicationService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @PostMapping(path = "", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
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

    @PutMapping(path = "/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<MedicationDto> updateMedication(@PathVariable Long id,
                                                          @RequestPart MultipartFile image,
                                                          @RequestPart @Pattern(regexp = "^[a-zA-Z0-9_-]+$") @NotBlank String name,
                                                          @RequestPart @Pattern(regexp = "^[A-Z0-9_]+$") @NotBlank String code,
                                                          @RequestPart @Pattern(regexp = "^[+]?[0-9]*\\.?[0-9]*$") @NotBlank String weight
    ) throws IOException {
        MedicationDto medicationDto = MedicationDto.builder()
                .image(image.getBytes())
                .code(code)
                .weight(Double.valueOf(weight))
                .name(name)
                .id(id)
                .build();

        MedicationDto savedMedication = medicationService.updateMedication(medicationDto);
        return new ResponseEntity<>(savedMedication, HttpStatus.OK);
    }


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteMedication(@PathVariable Long id){
        medicationService.deleteMedicationById(id);
        return new ResponseEntity<>("Medication with ID = " + id + " deleted successfully", HttpStatus.NO_CONTENT);
    }
}

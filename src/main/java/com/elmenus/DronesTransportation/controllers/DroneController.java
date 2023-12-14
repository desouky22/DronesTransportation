package com.elmenus.DronesTransportation.controllers;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.services.DroneService;
import com.elmenus.DronesTransportation.services.MedicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/drones")
public class DroneController {
    private DroneService droneService;
    private MedicationService medicationService;

    @Autowired
    public DroneController(DroneService droneService, MedicationService medicationService){
        this.droneService = droneService;
        this.medicationService = medicationService;
    }

    @GetMapping
    public ResponseEntity<List<DroneDto>> getAllDrones(){
        List<DroneDto> drones = droneService.getAllDrones();
        return new ResponseEntity<>(drones, HttpStatus.OK);
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<DroneDto> getDroneById(@PathVariable String serialNumber){
        DroneDto droneDto = droneService.getDroneById(serialNumber);
        return new ResponseEntity<>(droneDto, HttpStatus.OK);
    }

    @PutMapping("/{serialNumber}")
    public ResponseEntity<?> createUpdateDroneById(@PathVariable String serialNumber, @RequestBody @Valid DroneDto droneDto){
        droneDto.setSerialNumber(serialNumber);
        boolean isDroneExists = droneService.isDroneExists(droneDto);
        DroneDto savedDrone = droneService.save(droneDto);
        if(isDroneExists){
            return new ResponseEntity<>(savedDrone, HttpStatus.OK);
        }
        return new ResponseEntity<>(savedDrone, HttpStatus.CREATED);
    }

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<?> deleteDroneById(@PathVariable String serialNumber){
       droneService.deleteById(serialNumber);
       return new ResponseEntity<>("Medication with serialNumber = " + serialNumber + " deleted successfully", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{serialNumber}/medications")
    public ResponseEntity<List<MedicationDto>> getAllMedicationsOnDroneById(@PathVariable String serialNumber){
        List<MedicationDto> result =  medicationService.findByDroneId(serialNumber);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/available")
    public List<DroneDto> getAvailableDrones(){
        return droneService.getAvailableDrones();
    }
}

package com.elmenus.DronesTransportation.controllers;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.services.DroneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/drones")
public class DroneController {
    private DroneService droneService;

    @Autowired
    public DroneController(DroneService droneService){
        this.droneService = droneService;
    }

    @GetMapping
    public ResponseEntity<List<DroneDto>> getAllDrones(){
        List<DroneDto> drones = droneService.getAllDrones();
        return new ResponseEntity<>(drones, HttpStatus.OK);
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<DroneDto> getDroneWithId(@PathVariable String serialNumber){
        DroneDto droneDto = droneService.getDroneById(serialNumber);
        return new ResponseEntity<>(droneDto, HttpStatus.OK);
    }

    @PutMapping("/{serialNumber}")
    public ResponseEntity<?> createUpdateDroneWithId(@PathVariable String serialNumber, @RequestBody @Valid DroneDto droneDto){
        droneDto.setSerialNumber(serialNumber);
        boolean isDroneExists = droneService.isDroneExists(droneDto);
        DroneDto savedDrone = droneService.save(droneDto);
        if(isDroneExists){
            return new ResponseEntity<>(savedDrone, HttpStatus.OK);
        }
        return new ResponseEntity<>(savedDrone, HttpStatus.CREATED);
    }

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<?> deleteDroneWithId(@PathVariable String serialNumber){
        boolean deleted = droneService.deleteById(serialNumber);
        if(deleted){
            return new ResponseEntity<>("Deleted Successfully", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("There is no drone with this ID", HttpStatus.BAD_REQUEST);
    }
}

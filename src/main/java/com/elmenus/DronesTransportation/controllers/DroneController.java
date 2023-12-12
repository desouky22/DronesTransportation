package com.elmenus.DronesTransportation.controllers;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.services.DroneService;
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
    public ResponseEntity<?> getDroneWithId(@PathVariable String serialNumber){
        Optional<DroneDto> droneDto = droneService.getDroneById(serialNumber);
        if(droneDto.isPresent()){
            return new ResponseEntity<>(droneDto.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Drone not found with this ID", HttpStatus.NOT_FOUND);
    }
}

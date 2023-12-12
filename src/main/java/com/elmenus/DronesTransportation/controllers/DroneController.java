package com.elmenus.DronesTransportation.controllers;

import com.elmenus.DronesTransportation.domain.dtos.DroneDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;
import com.elmenus.DronesTransportation.mappers.DroneMapper;
import com.elmenus.DronesTransportation.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
}

package com.elmenus.DronesTransportation.controllers;

import com.elmenus.DronesTransportation.mappers.DroneMapper;
import com.elmenus.DronesTransportation.services.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/drones")
public class DroneController {
    private DroneService droneService;
    private DroneMapper droneMapper;
    @Autowired
    public DroneController(DroneService droneService, DroneMapper droneMapper){
        this.droneService = droneService;
        this.droneMapper = droneMapper;
    }
}

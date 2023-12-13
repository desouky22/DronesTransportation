package com.elmenus.DronesTransportation.repositories;

import com.elmenus.DronesTransportation.domain.dtos.MedicationDto;
import com.elmenus.DronesTransportation.domain.entities.Drone;
import com.elmenus.DronesTransportation.domain.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {

    @Query(value = "select m.id as id, m.name as name, m.code as code, m.weight as weight, m.drone_id as drone_id, m.image as image from drones d join medications m on m.drone_id = d.serial_number", nativeQuery = true)
    List<MedicationDto> findMedicationsByDroneId(String serialNumber);
}

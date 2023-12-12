package com.elmenus.DronesTransportation.repositories;

import com.elmenus.DronesTransportation.domain.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
}

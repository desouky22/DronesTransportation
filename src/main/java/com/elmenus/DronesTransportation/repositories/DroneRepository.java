package com.elmenus.DronesTransportation.repositories;

import com.elmenus.DronesTransportation.domain.entities.Drone;
import com.elmenus.DronesTransportation.utils.StateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, String> {
    List<Drone> findByState(StateEnum stateEnum);
}

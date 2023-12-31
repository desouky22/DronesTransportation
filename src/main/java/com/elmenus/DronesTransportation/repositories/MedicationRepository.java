package com.elmenus.DronesTransportation.repositories;

import com.elmenus.DronesTransportation.domain.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    Optional<Medication> findByCode(String code);
    List<Medication> findByDroneId(String droneId);
    List<Medication> findAllByCode(String code);
}

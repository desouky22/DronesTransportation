package com.elmenus.DronesTransportation.repositories;

import com.elmenus.DronesTransportation.domain.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
}

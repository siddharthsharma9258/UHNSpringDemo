package org.uhn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uhn.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {

}
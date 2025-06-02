package com.showxd.nurse_management.repository;

import com.showxd.nurse_management.model.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NurseRepository extends JpaRepository<Nurse, Long> {
    Nurse findByEmployeeId(String employeeId);
}

package com.showxd.nurse_management.repository;

import com.showxd.nurse_management.model.NurseStationAssignment;
import com.showxd.nurse_management.model.NurseStationId;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NurseStationAssignmentRepository
        extends JpaRepository<NurseStationAssignment, NurseStationId> {

    List<NurseStationAssignment> findByNurseId(Long nurseId);

    List<NurseStationAssignment> findByStationId(Long stationId);
}

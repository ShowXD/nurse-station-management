package com.showxd.nurse_management.service;

import com.showxd.nurse_management.dto.NurseDto;
import com.showxd.nurse_management.model.Nurse;
import com.showxd.nurse_management.model.NurseStationAssignment;
import com.showxd.nurse_management.model.NurseStationId;
import com.showxd.nurse_management.model.Station;
import com.showxd.nurse_management.repository.NurseRepository;
import com.showxd.nurse_management.repository.NurseStationAssignmentRepository;
import com.showxd.nurse_management.repository.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NurseService {

    private final NurseRepository nurseRepository;
    private final StationRepository stationRepository;
    private final NurseStationAssignmentRepository assignmentRepository;

    public NurseService(NurseRepository nurseRepository,
                        StationRepository stationRepository,
                        NurseStationAssignmentRepository assignmentRepository) {
        this.nurseRepository = nurseRepository;
        this.stationRepository = stationRepository;
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * 取得所有護士
     */
    public List<Nurse> getAllNurses() {
        return nurseRepository.findAll();
    }

    /**
     * 依照 ID 取得單一護士
     */
    public Optional<Nurse> getNurseById(Long id) {
        return nurseRepository.findById(id);
    }

    /**
     * 新增或更新護士
     */
    public Nurse saveNurse(Nurse nurse) {
        return nurseRepository.save(nurse);
    }

    /**
     * 刪除護士
     */
    public void deleteNurse(Long id) {
        nurseRepository.deleteById(id);
    }

    /**
     * 指派某位護士到指定站點
     */
    @Transactional
    public boolean assignStation(Long nurseId, Long stationId) {
        Optional<Nurse> optNurse = nurseRepository.findById(nurseId);
        Optional<Station> optStation = stationRepository.findById(stationId);

        if (optNurse.isEmpty() || optStation.isEmpty()) {
            return false;
        }

        Nurse nurse = optNurse.get();
        Station station = optStation.get();

        NurseStationId pk = new NurseStationId(nurseId, stationId);
        if (assignmentRepository.existsById(pk)) {
            return true;
        }

        NurseStationAssignment assignment = new NurseStationAssignment(nurse, station);
        assignmentRepository.save(assignment);
        return true;
    }

    /**
     * 取消某位護士與指定站點的關聯
     */
    @Transactional
    public boolean unassignStation(Long nurseId, Long stationId) {
        NurseStationId pk = new NurseStationId(nurseId, stationId);
        if (!assignmentRepository.existsById(pk)) {
            return false;
        }
        assignmentRepository.deleteById(pk);
        return true;
    }

    /**
     * 取得某位護士所有的 StationAssignment
     */
    public List<NurseStationAssignment> getAssignmentsByNurse(Long nurseId) {
        return assignmentRepository.findByNurseId(nurseId);
    }

    /**
     * 根據 NurseDto 更新指定 ID 的 Nurse
     */
    @Transactional
    public Optional<Nurse> updateNurseWithDto(Long id, NurseDto updatedDto) {
        return nurseRepository.findById(id)
            .map(existingNurse -> {
                existingNurse.setEmployeeId(updatedDto.getEmployeeId());
                existingNurse.setName(updatedDto.getName());

                Set<Long> newStationIds = Collections.emptySet();
                if (updatedDto.getStations() != null) {
                    newStationIds = updatedDto.getStations().stream()
                        .map(NurseDto.StationInfo::getId)
                        .collect(Collectors.toSet());
                }

                Set<NurseStationAssignment> existingAssignments = new HashSet<>(existingNurse.getStationAssignments());

                for (NurseStationAssignment assignment : existingAssignments) {
                    Long stationId = assignment.getStation().getId();
                    if (!newStationIds.contains(stationId)) {
                        existingNurse.getStationAssignments().remove(assignment);
                        assignment.getStation().getNurseAssignments().remove(assignment);
                        assignmentRepository.delete(assignment);
                    } else {
                        newStationIds.remove(stationId);
                    }
                }

                for (Long sid : newStationIds) {
                    stationRepository.findById(sid).ifPresent(station -> {
                        NurseStationAssignment newAssignment = new NurseStationAssignment(existingNurse, station);
                        existingNurse.getStationAssignments().add(newAssignment);
                        station.getNurseAssignments().add(newAssignment);
                        assignmentRepository.save(newAssignment);
                    });
                }

                return nurseRepository.save(existingNurse);
            });
    }
}

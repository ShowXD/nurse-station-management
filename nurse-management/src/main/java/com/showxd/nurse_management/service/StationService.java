package com.showxd.nurse_management.service;

import com.showxd.nurse_management.model.Nurse;
import com.showxd.nurse_management.model.NurseStationAssignment;
import com.showxd.nurse_management.model.NurseStationId;
import com.showxd.nurse_management.model.Station;
import com.showxd.nurse_management.repository.NurseRepository;
import com.showxd.nurse_management.repository.NurseStationAssignmentRepository;
import com.showxd.nurse_management.repository.StationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class StationService {

    private final StationRepository stationRepository;
    private final NurseRepository nurseRepository;
    private final NurseStationAssignmentRepository assignmentRepository;

    public StationService(StationRepository stationRepository,
                          NurseRepository nurseRepository,
                          NurseStationAssignmentRepository assignmentRepository) {
        this.stationRepository = stationRepository;
        this.nurseRepository = nurseRepository;
        this.assignmentRepository = assignmentRepository;
    }

    /**
     * 取得所有站點
     */
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    /**
     * 取得單一站點
     */
    public Optional<Station> getStationById(Long id) {
        return stationRepository.findById(id);
    }

    /**
     * 新增或更新站點
     */
    public Station saveStation(Station station) {
        return stationRepository.save(station);
    }

    /**
     * 刪除站點
     */
    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }

    /**
     * 取得某站點所有的 NurseStationAssignment
     */
    public List<NurseStationAssignment> getAssignmentsByStation(Long stationId) {
        return assignmentRepository.findByStationId(stationId);
    }

    /**
     * 指派某位護士到此站點
     */
    @Transactional
    public boolean assignNurseToStation(Long stationId, Long nurseId) {
        Optional<Station> optStation = stationRepository.findById(stationId);
        Optional<Nurse> optNurse = nurseRepository.findById(nurseId);

        if (optStation.isEmpty() || optNurse.isEmpty()) {
            return false;
        }

        Station station = optStation.get();
        Nurse nurse = optNurse.get();
        NurseStationId pk = new NurseStationId(nurseId, stationId);

        if (assignmentRepository.existsById(pk)) {
            return true;
        }

        NurseStationAssignment assignment = new NurseStationAssignment(nurse, station);
        assignmentRepository.save(assignment);
        return true;
    }

    /**
     * 取消此站點與某位護士的指派關聯
     */
    @Transactional
    public boolean unassignNurseFromStation(Long stationId, Long nurseId) {
        NurseStationId pk = new NurseStationId(nurseId, stationId);
        if (!assignmentRepository.existsById(pk)) {
            return false;
        }
        assignmentRepository.deleteById(pk);
        return true;
    }
}

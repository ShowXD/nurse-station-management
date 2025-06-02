// src/main/java/com/showxd/nurse_management/service/NurseService.java
package com.showxd.nurse_management.service;

import com.showxd.nurse_management.model.Nurse;
import com.showxd.nurse_management.model.Station;
import com.showxd.nurse_management.repository.NurseRepository;
import com.showxd.nurse_management.repository.StationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NurseService {

    private final NurseRepository nurseRepository;
    private final StationRepository stationRepository;

    public NurseService(NurseRepository nurseRepository,
                        StationRepository stationRepository) {
        this.nurseRepository = nurseRepository;
        this.stationRepository = stationRepository;
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
     * 根據 ID 刪除護士
     */
    public void deleteNurse(Long id) {
        nurseRepository.deleteById(id);
    }

    /**
     * 將指定的護士與站點做關聯（分配）。
     * @param nurseId   護士主鍵
     * @param stationId 站點主鍵
     * @return 修改後的 Nurse（含最新 stations）
     */
    @Transactional
    public Optional<Nurse> assignStation(Long nurseId, Long stationId) {
        Optional<Nurse> optNurse = nurseRepository.findById(nurseId);
        Optional<Station> optStation = stationRepository.findById(stationId);

        if (optNurse.isEmpty() || optStation.isEmpty()) {
            return Optional.empty();
        }

        Nurse nurse = optNurse.get();
        Station station = optStation.get();

        // 如果尚未關聯，才加入
        if (!nurse.getStations().contains(station)) {
            nurse.getStations().add(station);
            // 若需要維護雙向關聯，下面這行也要加：
            station.getNurses().add(nurse);
            nurseRepository.save(nurse);
            stationRepository.save(station);
        }

        return Optional.of(nurse);
    }

    /**
     * 取消指定護士與站點的關聯（取消分配）。
     * @param nurseId   護士主鍵
     * @param stationId 站點主鍵
     * @return 修改後的 Nurse（含最新 stations）
     */
    @Transactional
    public Optional<Nurse> unassignStation(Long nurseId, Long stationId) {
        Optional<Nurse> optNurse = nurseRepository.findById(nurseId);
        Optional<Station> optStation = stationRepository.findById(stationId);

        if (optNurse.isEmpty() || optStation.isEmpty()) {
            return Optional.empty();
        }

        Nurse nurse = optNurse.get();
        Station station = optStation.get();

        // 如果目前有關聯，才移除
        if (nurse.getStations().contains(station)) {
            nurse.getStations().remove(station);
            // 同步移除雙向
            station.getNurses().remove(nurse);
            nurseRepository.save(nurse);
            stationRepository.save(station);
        }

        return Optional.of(nurse);
    }
}

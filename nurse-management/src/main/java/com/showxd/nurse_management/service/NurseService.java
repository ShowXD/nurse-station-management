package com.showxd.nurse_management.service;

import com.showxd.nurse_management.dto.NurseDto;
import com.showxd.nurse_management.dto.StationDto;
import com.showxd.nurse_management.model.Nurse;
import com.showxd.nurse_management.model.Station;
import com.showxd.nurse_management.repository.NurseRepository;
import com.showxd.nurse_management.repository.StationRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
     * 將指定的護士與站點做關聯
     * @param nurseId
     * @param stationId
     * @return 修改後的 Nurse
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

        if (!nurse.getStations().contains(station)) {
            nurse.getStations().add(station);
            station.getNurses().add(nurse);
            nurseRepository.save(nurse);
            stationRepository.save(station);
        }

        return Optional.of(nurse);
    }

    /**
     * 取消指定護士與站點的關聯
     * @param nurseId
     * @param stationId
     * @return 修改後的 Nurse
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

        if (nurse.getStations().contains(station)) {
            nurse.getStations().remove(station);
            station.getNurses().remove(nurse);
            nurseRepository.save(nurse);
            stationRepository.save(station);
        }

        return Optional.of(nurse);
    }

    /**
     * 根據 NurseDto 更新指定 ID 的 Nurse。
     * @param id         護士主鍵
     * @param updatedDto 更新的資料傳輸物件
     * @return 更新後的
     */
    @Transactional
    public Optional<Nurse> updateNurseWithDto(Long id, NurseDto updatedDto) {
        return nurseRepository.findById(id)
            .map(existingNurse -> {
                existingNurse.setEmployeeId(updatedDto.getEmployeeId());
                existingNurse.setName(updatedDto.getName());

                Set<Station> newStations = new HashSet<>();
                if (updatedDto.getStations() != null) {
                    for (StationDto sd : updatedDto.getStations()) {
                        Long stationId = sd.getId();
                        stationRepository.findById(stationId).ifPresent(newStations::add);
                    }
                }
                existingNurse.setStations(newStations);

                return nurseRepository.save(existingNurse);
            });
    }
}

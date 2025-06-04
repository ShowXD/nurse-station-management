package com.showxd.nurse_management.service;

import com.showxd.nurse_management.model.Station;
import com.showxd.nurse_management.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StationService {

    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    /**
     * 取得所有站點
     */
    public List<Station> getAllStations() {
        return stationRepository.findAll();
    }

    /**
     * 依照 ID 取得單一站點
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
     * 根據 ID 刪除站點
     */
    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }
}

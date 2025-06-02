// src/main/java/com/showxd/nurse_management/controller/StationController.java
package com.showxd.nurse_management.controller;

import com.showxd.nurse_management.model.Station;
import com.showxd.nurse_management.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stations")
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping
    public List<Station> listAll() {
        return stationService.getAllStations();
    }

    @PostMapping
    public Station create(@RequestBody Station station) {
        return stationService.saveStation(station);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Station> update(
            @PathVariable Long id,
            @RequestBody Station updated) {

        return stationService.getStationById(id)
            .map(existing -> {
                existing.setName(updated.getName());
                existing.setNurses(updated.getNurses());
                Station saved = stationService.saveStation(existing);
                return ResponseEntity.ok(saved);
            })
            .orElseGet(() -> {
                updated.setId(id);
                Station saved = stationService.saveStation(updated);
                return ResponseEntity.ok(saved);
            });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (stationService.getStationById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }
}


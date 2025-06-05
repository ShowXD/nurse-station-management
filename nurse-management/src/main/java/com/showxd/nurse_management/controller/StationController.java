package com.showxd.nurse_management.controller;

import com.showxd.nurse_management.dto.StationDto;
import com.showxd.nurse_management.model.Station;
import com.showxd.nurse_management.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/stations")
public class StationController {

    private final StationService stationService;

    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    /** 取得全部站點 */
    @GetMapping
    public List<Station> listAll() {
        return stationService.getAllStations();
    }

    /** 根據 ID 取得單一站點（含已分派護士） */
    @GetMapping("/{id}")
    public ResponseEntity<StationDto> getById(@PathVariable Long id) {
        return stationService.getStationById(id)
            .map(station -> {
                List<StationDto.AssignedNurse> nurseList = station.getNurseAssignments().stream()
                    .map(assign -> new StationDto.AssignedNurse(
                        assign.getNurse().getId(),
                        assign.getNurse().getEmployeeId(),
                        assign.getNurse().getName(),
                        assign.getAssignedAt()
                    ))
                    .collect(Collectors.toList());

                StationDto dto = new StationDto(
                    station.getId(),
                    station.getName(),
                    nurseList
                );
                return ResponseEntity.ok(dto);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }


    /** 新增站點 */
    @PostMapping
    public Station create(@RequestBody Station station) {
        return stationService.saveStation(station);
    }

    /** 更新站點 */
    @PutMapping("/{id}")
    public ResponseEntity<Station> update(
            @PathVariable Long id,
            @RequestBody Station updated) {

        return stationService.getStationById(id)
                .map(existing -> {
                    existing.setName(updated.getName());
                    Station saved = stationService.saveStation(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> {
                    updated.setId(id);
                    Station saved = stationService.saveStation(updated);
                    return ResponseEntity.ok(saved);
                });
    }

    /** 刪除站點 */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (stationService.getStationById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        stationService.deleteStation(id);
        return ResponseEntity.noContent().build();
    }

    /** 取得某站點所有的護士指派紀錄 */
    @GetMapping("/{stationId}/nurses")
    public List<StationDto> getAllAssignmentsForStation(@PathVariable Long stationId) {
        return stationService.getAssignmentsByStation(stationId)
            .stream()
            .map(a -> new StationDto(
                a.getStation().getId(),
                a.getStation().getName(),
                List.of(new StationDto.AssignedNurse(
                    a.getNurse().getId(),
                    a.getNurse().getEmployeeId(),
                    a.getNurse().getName(),
                    a.getAssignedAt()
                ))
            ))
            .collect(Collectors.toList());
    }

    /** 指派某位護士到此站點 */
    @PostMapping("/{stationId}/nurses/{nurseId}")
    public ResponseEntity<?> assignNurse(
            @PathVariable Long stationId,
            @PathVariable Long nurseId) {
        boolean ok = stationService.assignNurseToStation(stationId, nurseId);
        return ok ? ResponseEntity.ok().build()
                  : ResponseEntity.notFound().build();
    }

    /** 取消某位護士在此站點的指派 */
    @DeleteMapping("/{stationId}/nurses/{nurseId}")
    public ResponseEntity<?> unassignNurse(
            @PathVariable Long stationId,
            @PathVariable Long nurseId) {
        boolean ok = stationService.unassignNurseFromStation(stationId, nurseId);
        return ok ? ResponseEntity.noContent().build()
                  : ResponseEntity.notFound().build();
    }
}

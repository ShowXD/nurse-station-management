package com.showxd.nurse_management.controller;

import com.showxd.nurse_management.dto.NurseDto;
import com.showxd.nurse_management.dto.StationDto;
import com.showxd.nurse_management.model.Nurse;
import com.showxd.nurse_management.service.NurseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/nurses")
public class NurseController {

    private final NurseService nurseService;

    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    /**
     * 取得所有護士
     * GET /api/nurses/
     */
    @GetMapping
    public List<Nurse> getAllNurses() {
        return nurseService.getAllNurses();
    }

    /**
     * 依照 ID 取得單一護士
     * GET /api/nurses/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<NurseDto> getNurseById(@PathVariable Long id) {
        return nurseService.getNurseById(id)
            .map(nurse -> {
                List<StationDto> stationDtoList = nurse.getStations().stream()
                    .map(st -> new StationDto(st.getId(), st.getName()))
                    .collect(Collectors.toList());

                NurseDto dto = new NurseDto(
                    nurse.getId(),
                    nurse.getEmployeeId(),
                    nurse.getName(),
                    nurse.getUpdatedAt(),
                    stationDtoList
                );

                return ResponseEntity.ok(dto);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 新增護士
     * POST /api/nurses
     * Content-Type: application/json
     */
    @PostMapping
    public Nurse createNurse(@RequestBody Nurse nurse) {
        return nurseService.saveNurse(nurse);
    }

    /**
     * 更新護士
     * PUT /api/nurses/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Nurse> updateNurse(
            @PathVariable Long id,
            @RequestBody NurseDto updatedDto) {

        return nurseService.updateNurseWithDto(id, updatedDto)
                             .map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 刪除護士
     * DELETE /api/nurses/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNurse(@PathVariable Long id) {
        if (nurseService.getNurseById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        nurseService.deleteNurse(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 分配護士到站點
     * POST /api/nurses/{nurseId}/stations/{stationId}
     */
    @PostMapping("/{nurseId}/stations/{stationId}")
    public ResponseEntity<Nurse> assignStation(
            @PathVariable Long nurseId,
            @PathVariable Long stationId) {

        return nurseService.assignStation(nurseId, stationId)
                .map(updatedNurse -> ResponseEntity.ok(updatedNurse))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 取消護士與站點關聯
     * DELETE /api/nurses/{nurseId}/stations/{stationId}
     */
    @DeleteMapping("/{nurseId}/stations/{stationId}")
    public ResponseEntity<Nurse> unassignStation(
            @PathVariable Long nurseId,
            @PathVariable Long stationId) {

        return nurseService.unassignStation(nurseId, stationId)
                .map(updatedNurse -> ResponseEntity.ok(updatedNurse))
                .orElse(ResponseEntity.notFound().build());
    }
}

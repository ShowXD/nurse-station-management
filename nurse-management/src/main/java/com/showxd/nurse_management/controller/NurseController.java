// src/main/java/com/showxd/nurse_management/controller/NurseController.java
package com.showxd.nurse_management.controller;

import com.showxd.nurse_management.model.Nurse;
import com.showxd.nurse_management.service.NurseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nurses")
public class NurseController {

    private final NurseService nurseService;

    public NurseController(NurseService nurseService) {
        this.nurseService = nurseService;
    }

    @GetMapping
    public List<Nurse> getAllNurses() {
        return nurseService.getAllNurses();
    }

    /**
     * 依照 ID 取得單一護士
     * GET /api/nurses/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Nurse> getNurseById(@PathVariable Long id) {
        return nurseService.getNurseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 新增護士
     * POST /api/nurses
     * Content-Type: application/json
     * Body: { "employeeId": "...", "name": "..." }
     */
    @PostMapping
    public Nurse createNurse(@RequestBody Nurse nurse) {
        return nurseService.saveNurse(nurse);
    }

    /**
     * 更新護士
     * PUT /api/nurses/{id}
     * Body: { "employeeId": "...", "name": "...", "stations": [ ... ] }
     */
    @PutMapping("/{id}")
    public ResponseEntity<Nurse> updateNurse(
            @PathVariable Long id,
            @RequestBody Nurse updatedNurse) {

        return nurseService.getNurseById(id)
                .map(existing -> {
                    existing.setEmployeeId(updatedNurse.getEmployeeId());
                    existing.setName(updatedNurse.getName());
                    existing.setStations(updatedNurse.getStations());
                    Nurse saved = nurseService.saveNurse(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> {
                    updatedNurse.setId(id);
                    Nurse saved = nurseService.saveNurse(updatedNurse);
                    return ResponseEntity.ok(saved);
                });
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

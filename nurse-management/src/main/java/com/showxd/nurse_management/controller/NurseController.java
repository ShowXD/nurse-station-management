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
     * GET /api/nurses
     */
    @GetMapping
    public List<NurseDto> getAllNurses() {
        List<Nurse> nurses = nurseService.getAllNurses();

        return nurses.stream()
            .map(nurse -> {
                // 只把「station id, name」轉到 StationInfo
                List<NurseDto.StationInfo> stationInfoList = nurse.getStationAssignments().stream()
                    .map(assign -> new NurseDto.StationInfo(
                        assign.getStation().getId(),
                        assign.getStation().getName()
                    ))
                    .collect(Collectors.toList());

                return new NurseDto(
                    nurse.getId(),
                    nurse.getEmployeeId(),
                    nurse.getName(),
                    nurse.getUpdatedAt(),
                    stationInfoList
                );
            })
            .collect(Collectors.toList());
    }

    /**
     * 依 ID 取得單一護士
     * GET /api/nurses/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<NurseDto> getNurseById(@PathVariable Long id) {
        return nurseService.getNurseById(id)
            .map(nurse -> {
                List<NurseDto.StationInfo> stationInfoList = nurse.getStationAssignments().stream()
                    .map(assign -> new NurseDto.StationInfo(
                        assign.getStation().getId(),
                        assign.getStation().getName()
                    ))
                    .collect(Collectors.toList());

                NurseDto dto = new NurseDto(
                    nurse.getId(),
                    nurse.getEmployeeId(),
                    nurse.getName(),
                    nurse.getUpdatedAt(),
                    stationInfoList
                );
                return ResponseEntity.ok(dto);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * 新增護士
     * POST /api/nurses
     */
    @PostMapping
    public Nurse createNurse(@RequestBody NurseDto nurseDto) {
        Nurse nurse = new Nurse();
        nurse.setEmployeeId(nurseDto.getEmployeeId());
        nurse.setName(nurseDto.getName());
        return nurseService.saveNurse(nurse);
    }

    /**
     * 更新護士
     * PUT /api/nurses/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<NurseDto> updateNurse(
            @PathVariable Long id,
            @RequestBody NurseDto updatedDto) {

        return nurseService.updateNurseWithDto(id, updatedDto)
            .map(nurse -> {
                List<NurseDto.StationInfo> stationInfoList = nurse.getStationAssignments().stream()
                    .map(assign -> new NurseDto.StationInfo(
                        assign.getStation().getId(),
                        assign.getStation().getName()
                    ))
                    .collect(Collectors.toList());

                NurseDto dto = new NurseDto(
                    nurse.getId(),
                    nurse.getEmployeeId(),
                    nurse.getName(),
                    nurse.getUpdatedAt(),
                    stationInfoList
                );
                return ResponseEntity.ok(dto);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
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

    /** 取得某位護士所有指派站點 */
    @GetMapping("/{nurseId}/stations")
    public List<StationDto> getAllAssignmentsForNurse(@PathVariable Long nurseId) {
        return nurseService.getAssignmentsByNurse(nurseId)
                .stream()
                .map(a -> {
                    return new StationDto(
                        a.getStation().getId(),
                        a.getStation().getName()
                    );
                })
                .collect(Collectors.toList());
    }

    /** 指派某位護士到指定站點 */
    @PostMapping("/{nurseId}/stations/{stationId}")
    public ResponseEntity<?> assignStation(
            @PathVariable Long nurseId,
            @PathVariable Long stationId) {

        boolean ok = nurseService.assignStation(nurseId, stationId);
        return ok ? ResponseEntity.ok().build()
                  : ResponseEntity.notFound().build();
    }

    /** 取消護士與指定站點的指派 */
    @DeleteMapping("/{nurseId}/stations/{stationId}")
    public ResponseEntity<?> unassignStation(
            @PathVariable Long nurseId,
            @PathVariable Long stationId) {

        boolean ok = nurseService.unassignStation(nurseId, stationId);
        return ok ? ResponseEntity.noContent().build()
                  : ResponseEntity.notFound().build();
    }
}

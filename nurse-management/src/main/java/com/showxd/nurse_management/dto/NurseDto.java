package com.showxd.nurse_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NurseDto {
    private Long id;
    private String employeeId;
    private String name;
    private LocalDateTime updatedAt;

    private List<StationInfo> stations;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StationInfo {
        private Long id;
        private String name;
    }
}

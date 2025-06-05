package com.showxd.nurse_management.dto;

import java.time.LocalDateTime;
import java.util.List;


public class StationDto {
    private Long id;
    private String name;
    private List<AssignedNurse> nurses;

    public StationDto() { }

    public StationDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public StationDto(Long id, String name, List<AssignedNurse> nurses) {
        this.id = id;
        this.name = name;
        this.nurses = nurses;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<AssignedNurse> getNurses() {
        return nurses;
    }
    public void setNurses(List<AssignedNurse> nurses) {
        this.nurses = nurses;
    }

    /**
     * 已分派到此站點的護士
     */
    public static class AssignedNurse {
        private Long id;
        private String employeeId;
        private String name;
        private LocalDateTime assignedAt;

        public AssignedNurse() { }

        public AssignedNurse(Long id, String employeeId, String name, LocalDateTime assignedAt) {
            this.id = id;
            this.employeeId = employeeId;
            this.name = name;
            this.assignedAt = assignedAt;
        }

        public Long getId() {
            return id;
        }
        public void setId(Long id) {
            this.id = id;
        }

        public String getEmployeeId() {
            return employeeId;
        }
        public void setEmployeeId(String employeeId) {
            this.employeeId = employeeId;
        }

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

        public LocalDateTime getAssignedAt() {
            return assignedAt;
        }
        public void setAssignedAt(LocalDateTime assignedAt) {
            this.assignedAt = assignedAt;
        }
    }
}

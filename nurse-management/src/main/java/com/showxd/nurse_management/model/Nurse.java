package com.showxd.nurse_management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "nurse")
public class Nurse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id", nullable = false, unique = true)
    private String employeeId;

    @Column(nullable = false)
    private String name;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "nurse", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<NurseStationAssignment> stationAssignments = new HashSet<>();

    public Nurse() { }

    public Nurse(String employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Transient
    public Set<Station> getStations() {
        return stationAssignments.stream()
            .map(NurseStationAssignment::getStation)
            .collect(Collectors.toSet());
    }

    public Set<NurseStationAssignment> getStationAssignments() {
        return stationAssignments;
    }

    public void setStationAssignments(Set<NurseStationAssignment> stationAssignments) {
        this.stationAssignments = stationAssignments;
    }

    public void assignStation(Station station) {
        NurseStationAssignment assignment = new NurseStationAssignment(this, station);
        stationAssignments.add(assignment);
        station.getNurseAssignments().add(assignment);
    }

    public void unassignStation(Station station) {
        stationAssignments.removeIf(a -> a.getStation().equals(station));
        station.getNurseAssignments().removeIf(a -> a.getNurse().equals(this));
    }
}

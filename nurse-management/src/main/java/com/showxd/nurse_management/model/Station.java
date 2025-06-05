package com.showxd.nurse_management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "station")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<NurseStationAssignment> nurseAssignments = new HashSet<>();

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Station() { }

    public Station(String name) {
        this.name = name;
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

    public Set<NurseStationAssignment> getNurseAssignments() {
        return nurseAssignments;
    }

    public void setNurseAssignments(Set<NurseStationAssignment> nurseAssignments) {
        this.nurseAssignments = nurseAssignments;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void assignNurse(Nurse nurse) {
        NurseStationAssignment assignment = new NurseStationAssignment(nurse, this);
        nurseAssignments.add(assignment);
        nurse.getStationAssignments().add(assignment);
    }

    public void unassignNurse(Nurse nurse) {
        nurseAssignments.removeIf(a -> a.getNurse().equals(nurse));
        nurse.getStationAssignments().removeIf(a -> a.getStation().equals(this));
    }
}

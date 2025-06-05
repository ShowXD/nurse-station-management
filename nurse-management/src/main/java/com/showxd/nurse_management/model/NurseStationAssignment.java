package com.showxd.nurse_management.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name = "nurse_station_assignment")
public class NurseStationAssignment {

    @EmbeddedId
    private NurseStationId id;

    @CreationTimestamp
    @Column(name = "assigned_at", nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @MapsId("nurseId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nurse_id", nullable = false)
    private Nurse nurse;

    @MapsId("stationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    protected NurseStationAssignment() { }

    public NurseStationAssignment(Nurse nurse, Station station) {
        this.nurse = nurse;
        this.station = station;
        this.id = new NurseStationId(nurse.getId(), station.getId());
    }


    public NurseStationId getId() {
        return id;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public Station getStation() {
        return station;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}

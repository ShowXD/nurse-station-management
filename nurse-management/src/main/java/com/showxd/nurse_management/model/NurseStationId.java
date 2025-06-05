package com.showxd.nurse_management.model;

import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Embeddable;


@Embeddable
public class NurseStationId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long nurseId;
    private Long stationId;

    public NurseStationId() { }

    public NurseStationId(Long nurseId, Long stationId) {
        this.nurseId = nurseId;
        this.stationId = stationId;
    }

    public Long getNurseId() {
        return nurseId;
    }

    public void setNurseId(Long nurseId) {
        this.nurseId = nurseId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NurseStationId)) return false;
        NurseStationId that = (NurseStationId) o;
        return Objects.equals(nurseId, that.nurseId) &&
               Objects.equals(stationId, that.stationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nurseId, stationId);
    }
}

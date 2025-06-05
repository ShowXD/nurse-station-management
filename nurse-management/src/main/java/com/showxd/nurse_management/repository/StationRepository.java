package com.showxd.nurse_management.repository;

import com.showxd.nurse_management.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
    Station findByName(String name);
}
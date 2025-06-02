package com.showxd.nurse_management.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // 自動遞增主鍵

    @Column(nullable = false, unique = true)
    private String name;                // 站點名稱，必填且唯一

    @ManyToMany(mappedBy = "stations")
    private Set<Nurse> nurses = new HashSet<>();

    public Station() {}

    public Station(String name) {
        this.name = name;
    }

    // —— Getter & Setter —— //

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

    public Set<Nurse> getNurses() {
        return nurses;
    }

    public void setNurses(Set<Nurse> nurses) {
        this.nurses = nurses;
    }
}

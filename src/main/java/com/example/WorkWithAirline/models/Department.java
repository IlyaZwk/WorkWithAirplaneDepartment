package com.example.WorkWithAirline.models;

import jakarta.persistence.*;

@Entity
@Table(name="department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String address;
    @Column
    private String departureTimeOfAirplane;






    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartureTimeOfAirplane() {
        return departureTimeOfAirplane;
    }

    public void setDepartureTimeOfAirplane(String departureTimeOfAirplane) {
        this.departureTimeOfAirplane = departureTimeOfAirplane;
    }
}
package com.example.WorkWithAirline.models;

import jakarta.persistence.*;

@Entity
@Table(name="route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String arrivalTime;
    @Column
    private String departureTime;
    @Column
    private Integer arrivalLocationId;
    @Column
    private Integer departureLocationId;







    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getArrivalLocationId() {
        return arrivalLocationId;
    }

    public void setArrivalLocationId(Integer arrivalLocationId) {
        this.arrivalLocationId = arrivalLocationId;
    }

    public Integer getDepartureLocationId() {
        return departureLocationId;
    }

    public void setDepartureLocationId(Integer departureLocationId) {
        this.departureLocationId = departureLocationId;
    }
}

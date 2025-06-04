package com.example.WorkWithAirline.models.Dto;

import jakarta.validation.constraints.NotEmpty;

public class PassengerDto {
    private Integer id;
    @NotEmpty(message="name is required")
    private String name;
    @NotEmpty(message="passport is required")
    private String passport;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport( String passport) {
        this.passport = passport;
    }
}

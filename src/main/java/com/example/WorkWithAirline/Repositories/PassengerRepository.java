package com.example.WorkWithAirline.Repositories;

import com.example.WorkWithAirline.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger,Integer> {
    Passenger findByPassport(String passport);
}

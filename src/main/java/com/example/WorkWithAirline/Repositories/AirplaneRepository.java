package com.example.WorkWithAirline.Repositories;

import com.example.WorkWithAirline.models.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneRepository extends JpaRepository<Airplane,Integer> {

}

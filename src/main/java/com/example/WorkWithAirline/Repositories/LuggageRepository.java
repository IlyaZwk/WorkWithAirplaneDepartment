package com.example.WorkWithAirline.Repositories;

import com.example.WorkWithAirline.models.Luggage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LuggageRepository extends JpaRepository<Luggage,Integer> {
}

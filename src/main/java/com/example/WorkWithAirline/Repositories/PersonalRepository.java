package com.example.WorkWithAirline.Repositories;

import com.example.WorkWithAirline.models.Personal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalRepository extends JpaRepository<Personal,Integer> {

}

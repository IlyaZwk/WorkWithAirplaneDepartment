package com.example.WorkWithAirline.Repositories;

import com.example.WorkWithAirline.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Integer> {

    AppUser findByUsername(String username);
    AppUser findByEmail(String email);
}

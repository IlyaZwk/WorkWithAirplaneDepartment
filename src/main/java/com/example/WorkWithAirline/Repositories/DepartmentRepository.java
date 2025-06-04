package com.example.WorkWithAirline.Repositories;

import com.example.WorkWithAirline.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
    Department findByAddress (String address);
}

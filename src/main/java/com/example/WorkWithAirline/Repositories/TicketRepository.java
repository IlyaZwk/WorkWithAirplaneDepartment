package com.example.WorkWithAirline.Repositories;

import com.example.WorkWithAirline.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    Ticket findByPassport(String passport);
}

package com.chickenfarms.chickenfarms.repository;

import com.chickenfarms.chickenfarms.model.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    
}

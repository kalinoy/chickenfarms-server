package com.chickenfarms.chickenfarms.repository;

import com.chickenfarms.chickenfarms.model.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    
    @Query(value = "SELECT * FROM chickenFarm_db.ticket_table ORDER BY created_date DESC limit ?1,?2",nativeQuery = true)
    Set<Ticket> getTicketsByPage(int startIndex,int endIndex);
    
    @Query(value = "SELECT * FROM chickenFarm_db.ticket_table WHERE status=?1 ORDER BY created_date DESC limit ?2,?3",nativeQuery = true)
    Set<Ticket> getTicketsByStatus(String status, int startIndex, int endIndex);
    
    @Query(value = "SELECT * FROM chickenFarm_db.ticket_table WHERE farm_id=?1 ORDER BY created_date DESC limit ?2,?3",nativeQuery = true)
    Set<Ticket> getTicketsByFarmId(int farmId,int startIndex,int endIndex);
    

    
}

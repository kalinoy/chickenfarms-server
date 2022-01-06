package com.chickenfarms.chickenfarms.repository;

import com.chickenfarms.chickenfarms.model.entity.CustomerInTicketPKId;
import com.chickenfarms.chickenfarms.model.entity.CustomersInTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomersInTicketRepository extends JpaRepository<CustomersInTicket, CustomerInTicketPKId> {

    @Query(value = "SELECT * FROM chickenFarm_db.customers_in_ticket_table WHERE ticket_ticket_id=?1",nativeQuery = true)
    Set<CustomersInTicket> getAllByTicket(long ticketId);

}

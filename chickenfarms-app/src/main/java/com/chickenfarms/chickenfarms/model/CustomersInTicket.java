package com.chickenfarms.chickenfarms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CustomersInTicketTable")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(CustomerInTicketPKId.class)
public class CustomersInTicket {

    @Id
    @ManyToOne
    @JoinColumn(name = "ticket_id")
//    @Column(name = "ticketId")
    private Ticket ticket;

    @Id
    @Column(name = "customerId")
    private int customerId;

    @Column(name = "addedDate")
    private Date addedDate;
}

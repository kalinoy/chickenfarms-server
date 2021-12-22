package com.chickenfarms.chickenfarms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CustomerInTicketPKId implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ticketId")
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Id
    @Column(name = "customerId")
    private int customerId;
}

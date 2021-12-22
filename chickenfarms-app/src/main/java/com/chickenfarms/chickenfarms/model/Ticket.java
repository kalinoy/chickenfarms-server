package com.chickenfarms.chickenfarms.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TicketTable")
public class Ticket  {

    @Id
    @Column(name = "ticketId",insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ticketId", unique = true,nullable = false)
    private int ticketId;

//    @Column(name = "createdById")
//    private int createdById;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "lastUpdatedDate")
    private Date lastUpdatedDate;

    @Column(name = "status")
    private String status;

    @Column(name = "sla")
    private int sla;

    @Column(name = "grade")
    private int grade;
    
//    @OneToMany(mappedBy = "ticket",fetch = FetchType.LAZY)
//    @OneToMany(mappedBy = "ticket",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "tickets")
    private Set<Tags> tags;
    
    //fetch when needed
    //maped by prevent from making more table with the connection
    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "problem_id", nullable = false, insertable = false, updatable = false)
    private Problem problem;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "root_cause_id")
    private RootCause rootCause;
    
    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private Set<CustomersInTicket> customersInTickets;


}

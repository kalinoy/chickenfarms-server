package com.chickenfarms.chickenfarms.model.entities;


import lombok.*;

import javax.persistence.*;
import java.util.*;

@Builder
@Getter
@Setter
@Entity
@Table(name = "TicketTable")
public class Ticket  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", unique = true,nullable = false)
    private long ticketId;

//    @Column(name = "createdById")
//    private int createdById;

    @Column(name = "createdDate")
    private Date createdDate=new Date(System.currentTimeMillis());

    @Column(name = "lastUpdatedDate")
    private Date lastUpdatedDate;

    @Column(name = "status")
    private String status;

    @Column(name = "sla")
    private int sla;

    @Column(name = "grade")
    private int grade;
    
    @Column(name = "decription")
    private String decription;
    
    @Column(name = "farmId")
    private int farmId;
    
//    @Column(name = "isResolved")
    private boolean isResolved;
    
//    @OneToMany(mappedBy = "ticket",fetch = FetchType.LAZY)
//    @OneToMany(mappedBy = "ticket",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "tickets")
    private Set<Tag> tags;
    
    //fetch when needed
    //maped by prevent from making more table with the connection
    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @ManyToOne
//    @JoinColumn(name = "problem_id", nullable = false, insertable = false, updatable = false)
    @JoinColumn(name = "problem_id")
    private Problem problem;
    
    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "root_cause_id", nullable = true)
    private RootCause rootCause;
    
    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object==null){
            return false;
        }
        if(object instanceof Ticket){
            Ticket ticket= (Ticket)object;
            if(ticket.getTicketId()==this.getTicketId()){
                return true;
            }
        }
        return false;
    }
    
//    @JsonIgnore
//    @OneToMany(targetEntity = CustomersInTicket.class,mappedBy = "ticket", fetch = FetchType.LAZY)
//    @JoinColumn(name = "ticket_id")
//    @OneToMany(targetEntity = CustomersInTicket.class, cascade = CascadeType.ALL)
////    @JoinColumn(name="ticket_id", referencedColumnName = "ticket_id")
//    @JoinColumn(name="ticket_id")
//
////    @OneToMany(mappedBy = "ticket",cascade = CascadeType.ALL)
////    @JoinColumn(name = "ticket_id",referencedColumnName = "id")
//    private Set<CustomersInTicket> customersInTickets=new HashSet<>();
    
//    @ManyToMany    
//    @JoinTable(name = "customers_in_ticket",
//            joinColumns = { @JoinColumn(name = "ticket_id") },
//            inverseJoinColumns = { @JoinColumn(name = "customer_id") })
//    private Set<Customer> customersInTickets=new HashSet<>();
    
//    @OneToMany(mappedBy = "ticket")
//    private Set<CustomersInTicket> customersInTicket=new HashSet<>();


}

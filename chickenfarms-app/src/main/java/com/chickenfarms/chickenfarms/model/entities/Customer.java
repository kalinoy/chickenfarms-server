package com.chickenfarms.chickenfarms.model;


import com.chickenfarms.chickenfarms.model.entities.CustomersInTicket;
import com.chickenfarms.chickenfarms.model.entities.Ticket;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Table(name="CustomerTable")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customer_id",unique = true,nullable = false)
    private long customerId;

    @Column(name = "customerUsername")
    private String customerUsername;

    @Column(name = "customerFirstName")
    private String customerFirstName;

    @Column(name = "customerSurname")
    private String customerSurname;
    
    @OneToMany(mappedBy = "customer")
    private Set<CustomersInTicket> customersInTicket=new HashSet<>();
}

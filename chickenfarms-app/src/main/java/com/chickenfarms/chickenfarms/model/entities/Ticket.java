package com.chickenfarms.chickenfarms.model.entities;


import lombok.*;

import javax.persistence.*;
import java.util.*;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TicketTable")
public class Ticket  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", unique = true,nullable = false)
    private long ticketId;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_updated_date")
    private Date lastUpdatedDate;

    @Column(name = "status")
    private String status;

    @Column(name = "sla")
    private int sla;

    @Column(name = "grade")
    private int grade;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "farm_id")
    private int farmId;
    
    @Column(name = "is_resolved")
    private boolean isResolved;
    
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "tickets")
    private Set<Tag> tags;
    
    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;
    
    @ManyToOne
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
}

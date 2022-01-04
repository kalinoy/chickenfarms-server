package com.chickenfarms.chickenfarms.model.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="CommentTable")
public class 


Comment {

    @Id
    @GeneratedValue
    @Column(name = "commentId")
    private long commentId;

    @ManyToOne
//    @JoinColumn(name = "ticket_id", nullable = false, insertable = false, updatable = false)
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @Column(name = "commentText")
    private String commentText;

//    @Column(name = "createdById")
//    private int createdById;

//    @Column(name = "ticketId")
//    private int ticketId;

    @Column(name = "createdDate")
    private Date createdDate;
    
    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    @JoinColumn(name = "user_id")
    private User user;


}

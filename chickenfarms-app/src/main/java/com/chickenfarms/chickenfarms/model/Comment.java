package com.chickenfarms.chickenfarms.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="CommentTable")
public class 


Comment {

    @Id
    @Column(name = "commentId")
    private int commentId;

    @ManyToOne
    @JoinColumn(name = "ticket_Id", nullable = false, insertable = false, updatable = false)
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
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;


}

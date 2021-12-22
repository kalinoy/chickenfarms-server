package com.chickenfarms.chickenfarms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="TagsTable")
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tagId",insertable = false, updatable = false)
    private int tagId;

    @Column(name = "tagName")
    private String tagName;
    
//    @ManyToMany(mappedBy = "tags")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tags_ticket",
        joinColumns = { @JoinColumn(name="tag_id")},
        inverseJoinColumns={@JoinColumn(name = "ticket_id")})
    private Set<Ticket> tickets;
    
//    @ManyToOne
//    @JoinColumn(name = "ticket_id")
////    @JoinColumn(name = "ticket_id", nullable = false, insertable = false, updatable = false)
//    private Ticket ticket;
}

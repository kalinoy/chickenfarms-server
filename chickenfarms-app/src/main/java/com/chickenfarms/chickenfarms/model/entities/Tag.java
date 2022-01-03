package com.chickenfarms.chickenfarms.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="TagsTable")
public class Tag {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="tagId",insertable = false, updatable = false)
//    private int tagId;

    @Id
    @Column(name = "tagName")
    private String tagName;
    
//    @ManyToMany(mappedBy = "tags")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tags_ticket",
        joinColumns = { @JoinColumn(name="tag_name")},
        inverseJoinColumns={@JoinColumn(name = "ticket_id")})
    private Set<Ticket> tickets;
    
//    @Override
//    public boolean equals(Object object){
//        if(this == object){
//            return true;
//        }
//        if(object==null){
//            return false;
//        }
//        if(object instanceof Tag){
//            Tag tag= object;
//            if(tag.tagName)
//        }
//    }
//    @ManyToOne
//    @JoinColumn(name = "ticket_id")
////    @JoinColumn(name = "ticket_id", nullable = false, insertable = false, updatable = false)
//    private Ticket ticket;
}

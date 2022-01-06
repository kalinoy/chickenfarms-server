package com.chickenfarms.chickenfarms.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="TagsTable")
public class Tag {

    @Id
    @Column(name = "tag_name")
    private String tagName;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "tags_ticket",
        joinColumns = { @JoinColumn(name="tag_name")},
        inverseJoinColumns={@JoinColumn(name = "ticket_id")})
    private Set<Ticket> tickets;

}

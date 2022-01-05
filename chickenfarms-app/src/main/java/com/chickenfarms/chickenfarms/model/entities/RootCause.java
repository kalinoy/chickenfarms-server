package com.chickenfarms.chickenfarms.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="RootCauseTable")
public class RootCause {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="root_cause_id",insertable = false, updatable = false)
    private int rootCauseId;
    
    @Column(name = "root_cause_name")
    private String rootCauseName;
    
    @OneToMany(mappedBy = "rootCause", fetch = FetchType.LAZY)
    private Set<Ticket> tickets;
    
    


}

package com.chickenfarms.chickenfarms.model.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="ProblemTable")
public class Problem {

    @Id
    @Column(name="problemId")
    private int problemId;

    @Column(name="problemName")
    private String problemName;
    
    
    //Lombok @Data generates toString for you and you are probably using bidirectiona
    @ToString.Exclude
    @OneToMany(mappedBy = "problem")
    private Set<Ticket> tickets;


}

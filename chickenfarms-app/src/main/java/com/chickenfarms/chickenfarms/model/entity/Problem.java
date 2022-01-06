package com.chickenfarms.chickenfarms.model.entity;

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
    @Column(name="problem_id")
    private int problemId;

    @Column(name="problem_name")
    private String problemName;
    
    @OneToMany(mappedBy = "problem")
    private Set<Ticket> tickets;


}

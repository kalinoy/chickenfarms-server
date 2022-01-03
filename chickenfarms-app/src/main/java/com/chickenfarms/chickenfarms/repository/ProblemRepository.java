package com.chickenfarms.chickenfarms.repository;

import com.chickenfarms.chickenfarms.model.entities.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository  extends JpaRepository<Problem, Integer> {
        
}

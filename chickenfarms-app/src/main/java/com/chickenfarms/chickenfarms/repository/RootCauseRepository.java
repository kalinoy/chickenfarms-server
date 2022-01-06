package com.chickenfarms.chickenfarms.repository;

import com.chickenfarms.chickenfarms.model.entity.RootCause;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RootCauseRepository extends JpaRepository<RootCause,Integer> {

    RootCause getByRootCauseName(String rootCauseName);
    
    Optional<RootCause> findByRootCauseName(String rootCauseName);
}

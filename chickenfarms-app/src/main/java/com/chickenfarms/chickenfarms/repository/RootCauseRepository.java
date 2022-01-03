package com.chickenfarms.chickenfarms.repository;

import com.chickenfarms.chickenfarms.model.entities.RootCause;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RootCauseRepository extends JpaRepository<RootCause,Integer> {

    RootCause getByRootCauseName(String rootCauseName);
}

package com.chickenfarms.chickenfarms.repository;

import com.chickenfarms.chickenfarms.model.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {
    
}

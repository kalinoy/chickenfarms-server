package com.chickenfarms.chickenfarms.repository;

import com.chickenfarms.chickenfarms.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}

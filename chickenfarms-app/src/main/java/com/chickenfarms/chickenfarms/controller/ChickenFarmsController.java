package com.chickenfarms.chickenfarms.controller;


import com.chickenfarms.chickenfarms.model.User;
import com.chickenfarms.chickenfarms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chickenFarms")
public class ChickenFarmsController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getUsers")
    public List<User> getAll(){
        return userRepository.findAll();
    }

}
